See the AD_SysConfig with name `de.metas.edi.OneDesadvPerShipment` for additional infos

"Multi" means that we have one DESADV for each Shipment.

| No      | Multi-Format                             | Multi-Table           | Note                                            |
|---------|------------------------------------------|-----------------------|-------------------------------------------------|
| 1       | EXP_M_InOut_Desadv_V                     | M_InOut_Desadv_V      |                                                 |
| 1.1     | M_InOut_Desadv_Pack_V                    | M_InOut_Desadv_Pack_V |                                                 |
| 1.1.1   | EDI_Desadv_Pack_Item_1PerInOut           | EDI_Desadv_Pack_Item  | (the actual physical table)                     |
| 1.1.1.1 | EXP_M_InOut_DesadvLine_V                 | M_InOut_DesadvLine_V  | whereclause=`IsDesadvLineInCurrentShipment='Y'` |
| 1.2     | EDI_Exp_DesadvLineWithNoPack_1PerInOut   | M_InOut_DesadvLine_V  | whereclause=`IsDesadvLineInCurrentShipment='N'` | 
| 1.2.1   | EDI_Exp_DesadvLineWithNoPack_1PerInOut_A | M_InOut_DesadvLine2_V | whereclause=`IsDesadvLineInCurrentShipment='N'` |

"Single" means that we have one DESADV for each ORDERS.

| No      | Single-Format                | Single-Table         | Note                                                                                        |
|---------|------------------------------|----------------------|---------------------------------------------------------------------------------------------|
| 1       | EDI_Exp_Desadv               | EDI_Desadv           |                                                                                             |
| 1.1     | EDI_Exp_Desadv_Pack          | EDI_Desadv_Pack      |
| 1.1     | EDI_Exp_Desadv_Pack_Item     | EDI_Desadv_Pack_Item |
| 1.1.1.1 | EDI_Exp_DesadvLine           | EDI_DesadvLine       |
| 1.2     | EDI_Exp_DesadvLineWithNoPack | EDI_DesadvLine       | whereclause=`EDI_DesadvLine_ID not in (select EDI_DesadvLine_ID from Edi_Desadv_Pack_Item)` |
| 1.2.1   | EDI_Exp_DesadvLine           | EDI_DesadvLine       |                                                                                             | 