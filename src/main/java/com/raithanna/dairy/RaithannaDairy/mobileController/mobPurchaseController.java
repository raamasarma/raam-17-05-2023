package com.raithanna.dairy.RaithannaDairy.mobileController;

import com.azure.core.annotation.Head;
import com.raithanna.dairy.RaithannaDairy.utilities.NumtoWords;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.raithanna.dairy.RaithannaDairy.models.*;
import com.raithanna.dairy.RaithannaDairy.repositories.*;
import com.sun.xml.bind.v2.util.ByteArrayOutputStreamEx;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class mobPurchaseController {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    BankRepository bankRepository;

    private DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("ddMMyy") ;
    private DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @PostMapping(value = "/getSupplyVehicleDetails", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getSupplyVehicleDetails(@RequestBody Map<String, String> body, Model model, HttpServletRequest request, HttpSession session ){
        System.out.println(body);
        Map body2 = new HashMap();
        List Vehicles = new ArrayList<>();
        List Suppliers = new ArrayList<>();
        List suplCode = new ArrayList();
        List Banks = new ArrayList<>();
        List MilkTypes = new ArrayList();
        List InvNumbers = new ArrayList();
        List batchNums = new ArrayList<>();
        List newInv = new ArrayList();
        LocalDate date = LocalDate.parse(body.get("date").toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        for (vehicle n :vehicleRepository.findAll()){
            List vehData =  new ArrayList<>();
            vehData.add(n.getVehicleNo());
            vehData.add(n.getSuplAttached());
            Vehicles.add(vehData);
        }
        for (supplier n :supplierRepository.findAll()){
            List data = new ArrayList();
            data.add(n.getSupplierName());
            data.add(n.getSupplierCode());
            Suppliers.add(data);
            suplCode.add(n.getSupplierCode());
        }

        for (bank n :bankRepository.findAll()){
            Banks.add(new String[]{ n.getIfsc(),n.getBankName()});
        }
        for (milktype n :milktypeRepository.findAll()){
            MilkTypes.add(n.getCode());
        }
        for(purchaseOrder order:purchaseOrderRepository.findByInvDate(body.get("date").toString())){
            if (!InvNumbers.contains(order.getInvNo().split("/")[0])){

            InvNumbers.add(order.getInvNo().split("/")[0]);
            batchNums.add(Integer.parseInt(order.getInvNo().split("/")[1]) + 1);
                suplCode.remove(order.getInvNo().split("/")[0].split("-")[0]);
            }


            int newInt = Integer.parseInt(order.getInvNo().split("/")[0].split("-")[2]) + 1;
            if (!InvNumbers.contains(order.getInvNo().split("/")[0].split("-")[0] + "-" + dateFormatter1.format(date) + "-00" + newInt) & purchaseOrderRepository.findByInvDateAndSuplCode(body.get("date").toString(), order.getSuplCode()).indexOf(order) == purchaseOrderRepository.findByInvDateAndSuplCode(body.get("date").toString(), order.getSuplCode()).size()-1) {
                InvNumbers.add(order.getInvNo().split("/")[0].split("-")[0] + "-" + dateFormatter1.format(date) + "-00" + newInt);
                newInv.add(order.getInvNo().split("/")[0].split("-")[0] + "-" + dateFormatter1.format(date) + "-00" + newInt);
                batchNums.add(1);
                suplCode.remove(order.getInvNo().split("/")[0].split("-")[0]);
            }
        }

        for(Object i : suplCode){
            List invData  = new ArrayList();
            batchNums.add(1);
            InvNumbers.add(i.toString()+"-"+dateFormatter1.format(date)+"-001");
        }
        System.out.println(InvNumbers);
        System.out.println(batchNums);
        body2.putIfAbsent("vehicles", Vehicles);
        body2.putIfAbsent("suppliers", Suppliers);
        body2.putIfAbsent("banks", Banks);
        body2.putIfAbsent("milktypes", MilkTypes);
        body2.putIfAbsent("invcNumbers", InvNumbers);
        body2.putIfAbsent("batchNums", batchNums);
        body2.putIfAbsent("newInvs", newInv);
        return ResponseEntity.status(202).body(body2);
    }


    @PostMapping(value = "/getInvs", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getInvs(@RequestBody Map<String, String> body, Model model, HttpServletRequest request, HttpSession session ){
        System.out.println(body);
        Map body2 = new HashMap();
        List InvNumbers = new ArrayList();
        LocalDate date = LocalDate.parse(body.get("date").toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        for(purchaseOrder order:purchaseOrderRepository.findByInvDate(body.get("date").toString())) {

            InvNumbers.add(order.getInvNo());


        }
        body2.putIfAbsent("invcNumbers", InvNumbers);
        return ResponseEntity.status(202).body(body2);
    }

    @GetMapping(value = "/getSuppliers")
    public ResponseEntity<Map> getSuppliers( HttpSession session ){
        Map body2 = new HashMap();
        List Suppliers = new ArrayList();

        for (supplier n :supplierRepository.findAll()){
            List data = new ArrayList();
            data.add(n.getSupplierName());
            data.add(n.getSupplierCode());
            Suppliers.add(data);
        }
        body2.putIfAbsent("suplrs", Suppliers);
        return ResponseEntity.ok().body(body2);
    }
    @PostMapping(value = "/purchaseOrderMob", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> savePurchases(@RequestBody Map<String, String> body, Model model, HttpServletRequest request, HttpSession session) {

        System.out.println("1111111111111111111111");
        System.out.println(body);
        purchaseOrder po=new purchaseOrder();
        po.setSuplCode(body.get("suplCode"));
        po.setSnfP(Double.parseDouble(body.get("snf")));
        po.setFatP(Double.parseDouble(body.get("fat")));
        po.setTsRate(Double.parseDouble(body.get("tsRate")));
        po.setLtrRate(Double.parseDouble(body.get("ltrRate")));
        po.setAmt(Double.parseDouble(body.get("amt")));
        po.setQuantity(Double.parseDouble(body.get("qty")));
        po.setMilkType(body.get("milkType"));
        po.setInvNo(body.get("inv"));
        po.setInvDate(body.get("date"));
        po.setVehNumber(body.get("vehicle"));
        po.setPaymentStatus(body.get("payStat"));

        if (body.get("payStat").equals("Received")) {
            po.setBankIfsc(body.get("bankifsc"));
            po.setRecDate(body.get("recvdate"));
            po.setBankrefno(body.get("bankrefno"));
            po.setRecAmount(Double.parseDouble(body.get("recamt")));
        }

        userModel user = userModelRepository.findByLoginID(body.get("loginId"));
        po.setAddedBy(user.getLoginID());
        po.setOrgCode(user.getOrgCode());
        po.setInvType(body.get("invType"));
        po.setTmode(body.get("Tmode"));
        po.setUnit(body.get("Unit"));
        po.setPoRefDate(body.get("porefdate"));
        po.setPoRefNo(body.get("porefno"));

        purchaseOrderRepository.save(po);

        Map<String,String> respBody = new HashMap<>();
        List<String> messages = new ArrayList<>();
        messages.add("Successfully Created");
        model.addAttribute("messages", messages );
        return ResponseEntity.status(202).body(new HashMap());

    }
    @PostMapping(value = "/updatepurchaseOrderMob", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> updatePurchases(@RequestBody Map<String, String> body, Model model, HttpServletRequest request, HttpSession session) {

        System.out.println("update");
        System.out.println(body);
        purchaseOrder po= purchaseOrderRepository.findByInvNo(body.get("inv"));
        po.setSnfP(Double.parseDouble(body.get("snf")));
        po.setFatP(Double.parseDouble(body.get("fat")));
        po.setTsRate(Double.parseDouble(body.get("tsRate")));
        po.setLtrRate(Double.parseDouble(body.get("ltrRate")));
        po.setAmt(Double.parseDouble(body.get("amt")));
        po.setQuantity(Double.parseDouble(body.get("qty")));
        po.setMilkType(body.get("milkType"));
        po.setInvDate(body.get("date"));
        po.setVehNumber(body.get("vehicle"));
        po.setPaymentStatus(body.get("payStat"));
        if (body.get("payStat").equals("Received")) {
            po.setBankIfsc(body.get("bankifsc"));
           // po.setBankrefno(body.get("bankRefno"));
            po.setRecDate(body.get("recvdate"));
            po.setRecAmount(Double.parseDouble(body.get("recamt")));
        }
        userModel user = userModelRepository.findByLoginID(body.get("loginId"));
        po.setAddedBy(user.getLoginID());
        po.setOrgCode(user.getOrgCode());
        po.setInvType(body.get("invType"));
        po.setTmode(body.get("Tmode"));
        po.setUnit(body.get("Unit"));
        po.setPoRefDate(body.get("porefdate"));
        po.setPoRefNo(body.get("porefno"));


        Map<String,String> respBody = new HashMap<>();
        List<String> messages = new ArrayList<>();
        messages.add("Successfully Updated");
        model.addAttribute("messages", messages );
        return ResponseEntity.status(202).body(new HashMap());

    }
    @PostMapping(value = "/getPO", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> getPO(@RequestBody Map<String, String> body, Model model, HttpServletRequest request, HttpSession session ){
        System.out.println(body);
        String invNumber = body.get("InvNo");
    if (invNumber.equals("null")){
        return ResponseEntity.status(202).body(new HashMap<>());
    }

        Map body2 = new HashMap();
        purchaseOrder po = purchaseOrderRepository.findByInvNo(invNumber);

        body2.putIfAbsent("InvNum" , po.getInvNo());
        body2.putIfAbsent("date" , po.getInvDate());
        body2.putIfAbsent("suplCode" , po.getSuplCode());
        body2.putIfAbsent("vehicle" , po.getVehNumber());
        body2.putIfAbsent("qty" , po.getQuantity());
        body2.putIfAbsent("fat" , po.getFatP());
        body2.putIfAbsent("snf" , po.getSnfP());
        body2.putIfAbsent("tsRate" , po.getTsRate());
        body2.putIfAbsent("ltrRate" , po.getLtrRate());
        body2.putIfAbsent("amt" , po.getAmt());
        body2.putIfAbsent("milkType" , po.getMilkType());
        body2.putIfAbsent("payStat" , po.getPaymentStatus());
        if (po.getPaymentStatus().equals("Received")) {
            body2.putIfAbsent("recvDate" , po.getRecDate().toString());
            //body2.putIfAbsent("bankrefno" , po.getBankrefno());
            body2.putIfAbsent("bankifsc" , po.getBankIfsc());
            body2.putIfAbsent("recAmt" , po.getRecAmount());
        }
        body2.putIfAbsent("invType", po.getInvType());
        body2.putIfAbsent("Tmode", po.getTmode());
        body2.putIfAbsent("unit",po.getUnit());
        body2.putIfAbsent("porefdate", po.getPoRefDate());
        body2.putIfAbsent("porefno", po.getPoRefNo());




        return ResponseEntity.status(202).body(body2);
    }
    @PostMapping(value = "/getPOExcel", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getPOExcel(@RequestBody Map<String, String> body, Model model, HttpServletResponse response, HttpSession session ) throws IOException {
        System.out.println(body);
        Map body2 = new HashMap();
        LocalDate datefrom = LocalDate.parse(body.get("fromdate").toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate dateto = LocalDate.parse(body.get("todate").toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<purchaseOrder> orders = purchaseOrderRepository.findByInvTypeAndInvDateBetween(body.get("invType") ,body.get("fromdate").toString(), body.get("todate").toString());
        if (!body.get("suplCode").equals("all")){orders = purchaseOrderRepository.findBySuplCodeAndInvTypeAndInvDateBetween(body.get("suplCode"),body.get("invType"), body.get("fromdate").toString(), body.get("todate").toString());}
        System.out.println(orders);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet =  workbook.createSheet(body.get("suplCode").toString());
        HSSFRow Header = sheet.createRow(0);

        int headercellStart = 0;
        String header[] = {"InvDate","InvNumber","supplier","InvType","TMode","vehicleNo","milkType","quantity","Units","fat","snf","tsRate", "ltrRate","Amount","po ref no", "po ref date","paymentStatus","bankIFSC","bankrefno","recdate", "rec amount"};
        for (String i: header){
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
         HSSFCell cell = Header.createCell(headercellStart);
         cell.setCellValue(i);
         cell.setCellStyle(style);
        headercellStart= headercellStart+ 1;
        }
        int rowVal = 1;
        for (purchaseOrder order: orders){
            System.out.println(order);
            HSSFRow row = sheet.createRow(rowVal);
            int cellval = 0;
            supplier supplier1 = supplierRepository.findBySupplierCode(order.getSuplCode());
            for (String i : order.getListValues(supplier1.getSupplierName())){
                System.out.println(i);
                HSSFCell cell = row.createCell(cellval);

                if (cellval == header.length-1 && order.getPaymentStatus().equals( "Received")){
                    cell.setCellValue(i);
                }else if (cellval != header.length-1 ) {
                    cell.setCellValue(i);
                }
                cellval= cellval+ 1;

            }
            rowVal= rowVal+1;
        }
        try {

            ByteArrayOutputStream ops = new ByteArrayOutputStream();
            workbook.write(ops);
            workbook.close();


            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+ body.get("supplier")).contentType(MediaType.APPLICATION_OCTET_STREAM).body(ops.toByteArray());
        }catch (Exception e){

        }


        return (ResponseEntity) ResponseEntity.status(203);
    }

    @PostMapping(value = "/getPOS", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List> getPOS(@RequestBody Map<String, String> body, Model model, HttpServletRequest request, HttpSession session ){
//        LocalDate date = LocalDate.parse(body.get("date").toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<purchaseOrder> orders = purchaseOrderRepository.findBySuplCodeAndInvDateAndInvType(body.get("suplCode"), body.get("date").toString(), body.get("invType").toString());
        System.out.println(orders);
        List orders_list = new ArrayList<>();

        for (purchaseOrder po: orders){
            Map body2 = new HashMap();
            body2.putIfAbsent("sno" , po.getInvNo().split("/")[1].toString());
            body2.putIfAbsent("InvNum" , po.getInvNo());
            body2.putIfAbsent("date" , po.getInvDate());
            body2.putIfAbsent("suplCode" , po.getSuplCode());
            body2.putIfAbsent("vehicle" , po.getVehNumber());
            body2.putIfAbsent("qty" , po.getQuantity());
            body2.putIfAbsent("fat" , po.getFatP());
            body2.putIfAbsent("snf" , po.getSnfP());
            body2.putIfAbsent("tsRate" , po.getTsRate());
            body2.putIfAbsent("ltrRate" , po.getLtrRate());
            body2.putIfAbsent("amt" , po.getAmt());
            body2.putIfAbsent("milkType" , po.getMilkType());
            body2.putIfAbsent("payStat" , po.getPaymentStatus());
            if (po.getPaymentStatus().equals("Received")) {
                body2.putIfAbsent("recvDate" , po.getRecDate().toString());
               // body2.putIfAbsent("bankrefno" , po.getBankrefno());
                body2.putIfAbsent("bankifsc" , po.getBankIfsc());
                body2.putIfAbsent("recAmt" , po.getRecAmount());
            }
            body2.putIfAbsent("invType", po.getInvType());
            body2.putIfAbsent("Tmode", po.getTmode());
            body2.putIfAbsent("unit",po.getUnit());
            body2.putIfAbsent("porefdate", po.getPoRefDate());
            body2.putIfAbsent("porefno", po.getPoRefNo());
            orders_list.add(body2);
        }



        return ResponseEntity.status(202).body(orders_list);
    }

    @Autowired
    SpringTemplateEngine springTemplateEngine;
    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private MilktypeRepository milktypeRepository;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    NumtoWords numtoWords;

    @PostMapping(value = "/getPOPDF", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getPOPDF(@RequestBody Map<String, String> body, Model model, HttpServletResponse response,HttpServletRequest request, HttpSession session ) throws IOException {
        System.out.println(body);
        LocalDate date = LocalDate.parse(body.get("date").toString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<purchaseOrder> orders = purchaseOrderRepository.findBySuplCodeAndInvDateAndInvType(body.get("suplCode"), body.get("date").toString(), body.get("invType"));
        System.out.println(orders);
        List<Map> orders_list = new ArrayList<>();
        Double ttlAmt = 0.0;
        for (purchaseOrder po: orders){

            if (body.get("invNum").equals(po.getInvNo().split("/")[0])){
                ttlAmt = ttlAmt + Double.parseDouble(po.getAmt().toString());
            Map body2 = new HashMap();
                body2.putIfAbsent("sno" , po.getInvNo().split("/")[1]);
            body2.putIfAbsent("InvNum" , po.getInvNo());
            body2.putIfAbsent("date" , po.getInvDate());
            body2.putIfAbsent("suplCode" , po.getSuplCode());
            body2.putIfAbsent("vehicle" , po.getVehNumber());
            body2.putIfAbsent("qty" , po.getQuantity());
            body2.putIfAbsent("fat" , po.getFatP());
            body2.putIfAbsent("snf" , po.getSnfP());
            body2.putIfAbsent("tsRate" , po.getTsRate());
            body2.putIfAbsent("ltrRate" , po.getLtrRate());
            body2.putIfAbsent("amt" , po.getAmt());
            body2.putIfAbsent("milkType" , po.getMilkType());
            body2.putIfAbsent("milkName",milktypeRepository.findByCode(po.getMilkType()).getName());
            body2.putIfAbsent("payStat" , po.getPaymentStatus());
            if (po.getPaymentStatus().equals("Received")) {
                body2.putIfAbsent("recvDate" , po.getRecDate().toString());
                //body2.putIfAbsent("bankrefno" , po.getBankrefno());
                body2.putIfAbsent("bankifsc" , po.getBankIfsc());
                body2.putIfAbsent("recAmt" , po.getRecAmount());
            }
            body2.putIfAbsent("invType", po.getInvType());
            body2.putIfAbsent("Tmode", po.getTmode());
            body2.putIfAbsent("unit",po.getUnit());
            body2.putIfAbsent("porefdate", po.getPoRefDate());
            body2.putIfAbsent("porefno", po.getPoRefNo());
            body2.putIfAbsent("orgCode", po.getOrgCode());
            orders_list.add(body2);}
        }
        company company_obj = companyRepository.findByOrgCode(orders_list.get(0).get("orgCode").toString());
        System.out.println(ttlAmt.intValue());
        String words = numtoWords.convert(ttlAmt.toString().split("\\.")[0]);

        supplier supplier_obj = supplierRepository.findBySupplierCode(orders_list.get(0).get("suplCode").toString());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("logo", company_obj.getOrgLogo());
        context.setVariable("banner", company_obj.getOrgBanner());
        context.setVariable("bannerPdf", body.get("ifBanner"));
        context.setVariable("words", words);
        context.setVariable("InvType",orders_list.get(0).get("invType").toString());
        context.setVariable("InvNum",orders_list.get(0).get("InvNum").toString());
        context.setVariable("InvDate",orders_list.get(0).get("date").toString());
        context.setVariable("Tmode",orders_list.get(0).get("Tmode").toString() );
        context.setVariable("org" , company_obj);
        context.setVariable("sup" , supplier_obj);
        context.setVariable("orders", orders_list);
        context.setVariable("ttlamt", ttlAmt);
        String finalhtml = springTemplateEngine.process("purchasepdfMob",context);
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(finalhtml);
        renderer.layout();
        renderer.createPDF(ops, false);
        renderer.finishPDF();
        try {

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+ body.get("supplier")+".pdf").contentType(MediaType.APPLICATION_OCTET_STREAM).body(ops.toByteArray());
        }catch (Exception e){

        }


        return ResponseEntity.ok(body);
    }

}

