package com.raithanna.dairy.RaithannaDairy.controller;
import com.lowagie.text.pdf.PdfWriter;
import com.raithanna.dairy.RaithannaDairy.ServiceImpl.Utility.DownloadCsvReport;
import com.raithanna.dairy.RaithannaDairy.models.*;
import com.raithanna.dairy.RaithannaDairy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.Document;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Controller
public class purchaseOrderController {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/purchase")
    public String purchaseOrderForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            List<supplier> Suppliers = supplierRepository.findByOrderByIdDesc();
            //List<purchaseOrder> Amts = purchaseOrderRepository.findByOrderByAmtDesc();
            List<bank> bank = bankRepository.findByOrderByIdDesc();
            List<vehicle> vehicle =vehicleRepository.findByOrderByIdDesc();
//            purchaseOrder purchaseOrder = purchaseOrderRepository.findTopByOrderByOrderNoDesc();
//            Integer orderNo;
//            if (purchaseOrder==null) {
//                orderNo = 1;
//            } else {
//                orderNo = purchaseOrder.getOrderNo() + 1;
//            }
            System.out.println(Suppliers.size());
            purchaseOrder po = new purchaseOrder();
            model.addAttribute("purchase", po);
            model.addAttribute("supplier", Suppliers);
           // model.addAttribute("amt", Amts);
            model.addAttribute("bank", bank);
            model.addAttribute("vehicle", vehicle);
          //  model.addAttribute("orderNo", orderNo);
            return "purchase";
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }
   // @GetMapping("/getPurchase")
//    public String getPurchase(@RequestParam(name = "orderNo", defaultValue = "1") Integer orderNo, Model model) {
//        List<purchaseOrder> list = purchaseOrderRepository.findByOrderNo(orderNo);
//        model.addAttribute("list", list);
//        return "purchaseDisplay";
//    }
   @GetMapping("/viewInvoice")
   public String viewInvoice(@RequestParam(name = "invoiceNo", defaultValue = "1") String invoiceNo,@RequestParam(name = "orgCode", defaultValue = "1") String orgCode, Model model) {
       //List<purchaseOrder> po = purchaseOrderRepository.findByInvNo(invoiceNo);
       purchaseOrder po = purchaseOrderRepository.findByInvNo(invoiceNo);
       // purchaseOrder oc = purchaseOrderRepository.findByOrgCode(orgCode);
       String date=po.getInvDate();
       String OrgCode= po.getOrgCode();
       String suplCode =po.getSuplCode();
       System.out.println(suplCode);
       System.out.println(OrgCode);
       supplier sm= supplierRepository.findBySupplierCode(suplCode);
       System.out.println("sm:"+sm);
       
       company cp= companyRepository.findByOrgCode(orgCode);
       System.out.println("cp:"+cp);
       model.addAttribute("invoiceNo",invoiceNo);
       model.addAttribute("po",po);
       // model.addAttribute("date",date);
       model.addAttribute("sm",sm);
       model.addAttribute("cp",cp);
       return "purchasePdf";
   }

//    @PostMapping("/pdfDownload")
//    public String pdfDownload(@RequestParam Map<String, String> body, Model model, HttpServletResponse response, HttpServletRequest request) {
//        System.out.println(body);
//        System.out.println("invoiceNo:" + body.get("invoiceNo"));
//        String invNo = body.get("invoiceNo");
//        purchaseOrder po = purchaseOrderRepository.findByInvNo(invNo);
//
//        // create pdf logic
//        boolean flag = createPdfFile(po);
//
//        return "";
//
//    }
//
//    private boolean createPdfFile(purchaseOrder po)
//    {
//        try {
//            Document doc = new Document();
//            PdfWriter pdfWriter = null;
//
//            String path = "D:\\krupa\\po.pdf";
//            pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
//            // pdfWriter.setPageEvent((PdfPageEvent) new PDFEven
//            doc.addCreationDate();
//            doc.addProducer();
//            doc.addCreator("Application");
//            doc.addTitle("Invoice");
//            doc.setPageSize(PageSize.LETTER);
//
////
//            doc.open();
//            Paragraph p1 = new Paragraph("Invoice Number");
//
//            doc.add(p1);
//            doc.close();
//        }
//        catch(Exception e){
//
//        }
//
//        return true;
//    }

    @GetMapping("/purchaseExcelData")
    public String purchaseExcelOrderForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            List<supplier> Suppliers = supplierRepository.findByOrderByIdDesc();
            purchaseOrder po = new purchaseOrder();
            model.addAttribute("purchase", po);
            model.addAttribute("supplier", Suppliers);
            return "purchaseExcel";
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }
    @PostMapping("/purchaseOrderNew")
    public ModelAndView purchaseOrderForm(Model model,@RequestBody List<purchaseOrder> purchaseList, HttpSession session) {
        String invNo ="";
        if (session.getAttribute("loggedIn").equals("yes")) {
            // List<purchaseOrder> list1 = purchaseOrderRepository.findBySupplierAndInvDate(purchaseList.get(0).getSupplier(),purchaseList.get(0).getInvDate());

            System.out.println(purchaseList.get(0).getInvDate());
            List<purchaseOrder> list1 = purchaseOrderRepository.findByInvDate(purchaseList.get(0).getInvDate());
            Set<String> supplierName = new HashSet<>();
            for (purchaseOrder str : list1) {
                System.out.println("set --"+str.getSuplCode()+"-"+str.getInvDate());
                supplierName.add(str.getInvNo());
            }
            System.out.println(list1.size());
            System.out.println(supplierName.size());
            String format = String.format("%03d", supplierName.size() + 1);
            invNo =  purchaseList.get(0).getInvDate() + "-" + purchaseList.get(0).getSuplCode() + "-" + format + "/";
            int subOrder = 0;
            for (purchaseOrder list : purchaseList) {
                subOrder = subOrder+1;
                purchaseOrder po = new purchaseOrder();
                // sno from ui
               // po.setSlNo(list.getSlNo());
                po.setInvDate(list.getInvDate());
                po.setSupplier(list.getSupplier());
                po.setVehNumber(list.getVehNumber());
                po.setMilkType(list.getMilkType());
                po.setQuantity(list.getQuantity());
                po.setFatP(list.getFatP());
                po.setSnfP(list.getSnfP());
                po.setTsRate(list.getTsRate());
                po.setLtrRate(list.getLtrRate());
                po.setAmt(list.getAmt());
                //po.setOrderNo(list.getOrderNo());
                po.setPaymentStatus(list.getPaymentStatus());
               // po.setBankrefno(list.getBankrefno());
                po.setBankIfsc(list.getBankIfsc());
                po.setRecDate(list.getRecDate());
                po.setSuplCode(list.getSuplCode());
                po.setInvNo(invNo+subOrder);
               // po.setCode(list.getCode());
                po.setInvType(list.getInvType());
                po.setTmode(list.getTmode());
                po.setUnit(list.getUnit());
                po.setPoRefNo(list.getPoRefNo());
                po.setPoRefDate(list.getPoRefDate());
                po.setOrgCode(list.getOrgCode());
                po.setTotAmtRound(list.getTotAmtRound());
                po.setBankrefno(list.getBankrefno());
                purchaseOrderRepository.save(po);
            }
            return new ModelAndView("/loginPage");
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return new ModelAndView("/loginPage");
    }
    @PostMapping("/purchaseExcelData")
    public String purchaseExcelData(@RequestParam Map<String, String> body, Model model, HttpServletResponse response, HttpServletRequest request) {
        System.out.println(body);
        purchaseOrder po = new purchaseOrder();
        System.out.println("SupplierCode:" + body.get("code"));
        po.setSupplier(body.get("supplierName"));
        po.setInvDate(body.get("invDate"));
        po.setRecDate(body.get("recDate"));
        // excel data list
        System.out.println("Supplier:" + body.get("supplier"));
        System.out.println("Supplier:" +po.getSupplier());
        List<purchaseOrder> list = purchaseOrderRepository.findBySuplCodeAndInvDateBetween(po.getSupplier(),po.getInvDate(),po.getRecDate());
        String header[] = {"invDate","supplier","vehNumber","quantity","fatP","snfP","tsRate","milkType","bankIfsc","paymentStatus"};
        DownloadCsvReport.getCsvReportDownload(response, header, list, "invoice_data.csv");
        System.out.println("Excel Size -- "+list.size());
        return "redirect:/purchaseExcelData";
    }
    @GetMapping("/purchaseViewData")
    public String purchaseViewOrderForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            List<supplier> Suppliers = supplierRepository.findByOrderByIdDesc();
            purchaseOrder po = new purchaseOrder();
            model.addAttribute("purchase", po);
            model.addAttribute("supplier", Suppliers);
            return "purchaseView";
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }
    @PostMapping("/purchaseViewData")
    public String purchaseOrder(@RequestParam Map<String, String> body, Model model, HttpServletResponse response, HttpServletRequest request) {
        System.out.println(body);
        purchaseOrder po = new purchaseOrder();
        System.out.println("SupplierName:" + body.get("supplierName"));
        po.setSupplier(body.get("supplierName"));
        var supplier=body.get("SupplierName");
        po.setInvDate(body.get("invDate"));
        System.out.println(body.get("invDate"));
        po.setRecDate(body.get("recDate"));
        System.out.println("supplier:"+po.getSuplCode());
        System.out.println("supplier:"+po.getSupplier());
        // excel data list
        List<purchaseOrder> list = purchaseOrderRepository.findBySuplCodeAndInvDateBetween(po.getSupplier(), po.getInvDate(),po.getRecDate());
       // List<purchaseOrder> list = purchaseOrderRepository.findBySuplCode(po.getSupplier());
        List<supplier> Suppliers = supplierRepository.findByOrderByIdDesc();
        model.addAttribute("supplier", Suppliers);
        model.addAttribute("list", list);
        System.out.println("Excel Size -- " + list.size());
        return "/purchaseView";
    }


}
