package com.raithanna.dairy.RaithannaDairy.models;

import com.raithanna.dairy.RaithannaDairy.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Map;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class purchaseOrder {
    @Id
    @GeneratedValue
    private Integer id;
   //private Integer slNo;
    private String invDate;
  // private Integer orderNo;/// found redundant - need to remove- as this is part of invno
    private String invNo;//VA-120523-001/01 S1-260423-001/2
    private String supplier;//need to remove
    private String suplCode;//supplier.suppliercode
    private String vehNumber;//vehicle.vehicleNo
    private String milkType;//milktype.code
    private Double quantity;//as per unit
    private Double fatP;//decimal 2
    private Double snfP;//decimal 2
    private Double tsRate;//decimal 2
    private Double ltrRate;//Unit Rate <Ltr rate or KG Rate - depends on unit>private Double amt;// Item wise Total
    private Double amt;
    private String invType;//Purchase Invoice or Tax(Sale) Invoice
    private String tmode;// Road or Others
    private String unit;// KG / Ltr
    private String poRefNo;// NA
    private String poRefDate;//NA
    private Double totAmtRound;
    private String orgCode;

    private String addedBy;
    private String paymentStatus;
    private String bankIfsc;
    private String bankrefno;
    private String recDate;

    private Double recAmount = 0.0;



    public String[] getListValues(String supplierName){
        //{"InvDate","InvNumber","supplier","InvType","TMode","vehicleNo","milkType","quantity","Units","fat","snf","tsRate", "ltrRate","Amount","po ref no", "po ref date","paymentStatus","bankIFSC","bankrefno","recdate", "rec amount", ""};
        String values[] = {this.invDate.toString(), this.invNo,supplierName,this.invType, this.tmode, this.vehNumber, this.milkType,this.quantity.toString(),this.unit, this.fatP.toString(), this.snfP.toString(), this.tsRate.toString(), this.ltrRate.toString(),this.amt.toString(), this.poRefNo, this.poRefDate, this.paymentStatus, this.bankIfsc,this.bankrefno,this.recDate};
        return values;
    }



}
