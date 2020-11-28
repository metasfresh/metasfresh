-- Generated with class de.metas.adempiere.tools.AD_Ref_List_ValueName_UpdateFromClass by tsa


-- DELIVERYRULE_
UPDATE AD_Ref_List SET ValueName='AfterReceipt' WHERE AD_Reference_ID=151 AND Value='R';
UPDATE AD_Ref_List SET ValueName='Availability' WHERE AD_Reference_ID=151 AND Value='A';
UPDATE AD_Ref_List SET ValueName='CompleteLine' WHERE AD_Reference_ID=151 AND Value='L';
UPDATE AD_Ref_List SET ValueName='CompleteOrder' WHERE AD_Reference_ID=151 AND Value='O';
UPDATE AD_Ref_List SET ValueName='Force' WHERE AD_Reference_ID=151 AND Value='F';
UPDATE AD_Ref_List SET ValueName='Manual' WHERE AD_Reference_ID=151 AND Value='M';
UPDATE AD_Ref_List SET ValueName='MitNaechsterAbolieferung' WHERE AD_Reference_ID=151 AND Value='S';


-- DELIVERYVIARULE_
UPDATE AD_Ref_List SET ValueName='Pickup' WHERE AD_Reference_ID=152 AND Value='P';
UPDATE AD_Ref_List SET ValueName='Delivery' WHERE AD_Reference_ID=152 AND Value='D';
UPDATE AD_Ref_List SET ValueName='Shipper' WHERE AD_Reference_ID=152 AND Value='S';


-- DOCACTION_
UPDATE AD_Ref_List SET ValueName='Complete' WHERE AD_Reference_ID=135 AND Value='CO';
UPDATE AD_Ref_List SET ValueName='Approve' WHERE AD_Reference_ID=135 AND Value='AP';
UPDATE AD_Ref_List SET ValueName='Reject' WHERE AD_Reference_ID=135 AND Value='RJ';
UPDATE AD_Ref_List SET ValueName='Post' WHERE AD_Reference_ID=135 AND Value='PO';
UPDATE AD_Ref_List SET ValueName='Void' WHERE AD_Reference_ID=135 AND Value='VO';
UPDATE AD_Ref_List SET ValueName='Close' WHERE AD_Reference_ID=135 AND Value='CL';
UPDATE AD_Ref_List SET ValueName='Reverse_Correct' WHERE AD_Reference_ID=135 AND Value='RC';
UPDATE AD_Ref_List SET ValueName='Reverse_Accrual' WHERE AD_Reference_ID=135 AND Value='RA';
UPDATE AD_Ref_List SET ValueName='Invalidate' WHERE AD_Reference_ID=135 AND Value='IN';
UPDATE AD_Ref_List SET ValueName='Re_Activate' WHERE AD_Reference_ID=135 AND Value='RE';
UPDATE AD_Ref_List SET ValueName='None' WHERE AD_Reference_ID=135 AND Value='--';
UPDATE AD_Ref_List SET ValueName='Prepare' WHERE AD_Reference_ID=135 AND Value='PR';
UPDATE AD_Ref_List SET ValueName='Unlock' WHERE AD_Reference_ID=135 AND Value='XL';
UPDATE AD_Ref_List SET ValueName='WaitComplete' WHERE AD_Reference_ID=135 AND Value='WC';


-- DOCSTATUS_
UPDATE AD_Ref_List SET ValueName='Drafted' WHERE AD_Reference_ID=131 AND Value='DR';
UPDATE AD_Ref_List SET ValueName='Completed' WHERE AD_Reference_ID=131 AND Value='CO';
UPDATE AD_Ref_List SET ValueName='Approved' WHERE AD_Reference_ID=131 AND Value='AP';
UPDATE AD_Ref_List SET ValueName='NotApproved' WHERE AD_Reference_ID=131 AND Value='NA';
UPDATE AD_Ref_List SET ValueName='Voided' WHERE AD_Reference_ID=131 AND Value='VO';
UPDATE AD_Ref_List SET ValueName='Invalid' WHERE AD_Reference_ID=131 AND Value='IN';
UPDATE AD_Ref_List SET ValueName='Reversed' WHERE AD_Reference_ID=131 AND Value='RE';
UPDATE AD_Ref_List SET ValueName='Closed' WHERE AD_Reference_ID=131 AND Value='CL';
UPDATE AD_Ref_List SET ValueName='Unknown' WHERE AD_Reference_ID=131 AND Value='??';
UPDATE AD_Ref_List SET ValueName='InProgress' WHERE AD_Reference_ID=131 AND Value='IP';
UPDATE AD_Ref_List SET ValueName='WaitingPayment' WHERE AD_Reference_ID=131 AND Value='WP';
UPDATE AD_Ref_List SET ValueName='WaitingConfirmation' WHERE AD_Reference_ID=131 AND Value='WC';


-- FREIGHTCOSTRULE_
UPDATE AD_Ref_List SET ValueName='FreightIncluded' WHERE AD_Reference_ID=153 AND Value='I';
UPDATE AD_Ref_List SET ValueName='FixPrice' WHERE AD_Reference_ID=153 AND Value='F';
UPDATE AD_Ref_List SET ValueName='Calculated' WHERE AD_Reference_ID=153 AND Value='C';
UPDATE AD_Ref_List SET ValueName='Line' WHERE AD_Reference_ID=153 AND Value='L';
UPDATE AD_Ref_List SET ValueName='Versandkostenpauschale' WHERE AD_Reference_ID=153 AND Value='P';


-- INCOTERM_
UPDATE AD_Ref_List SET ValueName='EXW_AbWerk' WHERE AD_Reference_ID=501599 AND Value='EXW';
UPDATE AD_Ref_List SET ValueName='FCA_FreiSpediteur' WHERE AD_Reference_ID=501599 AND Value='FCA';
UPDATE AD_Ref_List SET ValueName='FAS_FreiLaengsseitsSchiff' WHERE AD_Reference_ID=501599 AND Value='FAS';
UPDATE AD_Ref_List SET ValueName='FOB_FreiAnBord' WHERE AD_Reference_ID=501599 AND Value='FOB';
UPDATE AD_Ref_List SET ValueName='CFR_KostenUndFracht' WHERE AD_Reference_ID=501599 AND Value='CFR';
UPDATE AD_Ref_List SET ValueName='CIF_KostenVersicherungUndFracht' WHERE AD_Reference_ID=501599 AND Value='CIF';
UPDATE AD_Ref_List SET ValueName='CPT_FrachtPortoBezahltBis' WHERE AD_Reference_ID=501599 AND Value='CPT';
UPDATE AD_Ref_List SET ValueName='CIP_FrachtPortoUndVersicherungBezahltBis' WHERE AD_Reference_ID=501599 AND Value='CIP';
UPDATE AD_Ref_List SET ValueName='DAF_FreiGrenze' WHERE AD_Reference_ID=501599 AND Value='DAF';
UPDATE AD_Ref_List SET ValueName='DES_FreiAbSchiff' WHERE AD_Reference_ID=501599 AND Value='DES';
UPDATE AD_Ref_List SET ValueName='DEQ_FreiAbKai' WHERE AD_Reference_ID=501599 AND Value='DEQ';
UPDATE AD_Ref_List SET ValueName='DDU_FreiUnverzollt' WHERE AD_Reference_ID=501599 AND Value='DDU';
UPDATE AD_Ref_List SET ValueName='DDP_Verzollt' WHERE AD_Reference_ID=501599 AND Value='DDP';


-- MOVEMENTTYPE_
UPDATE AD_Ref_List SET ValueName='CustomerShipment' WHERE AD_Reference_ID=189 AND Value='C-';
UPDATE AD_Ref_List SET ValueName='CustomerReturns' WHERE AD_Reference_ID=189 AND Value='C+';
UPDATE AD_Ref_List SET ValueName='VendorReceipts' WHERE AD_Reference_ID=189 AND Value='V+';
UPDATE AD_Ref_List SET ValueName='VendorReturns' WHERE AD_Reference_ID=189 AND Value='V-';
UPDATE AD_Ref_List SET ValueName='InventoryOut' WHERE AD_Reference_ID=189 AND Value='I-';
UPDATE AD_Ref_List SET ValueName='InventoryIn' WHERE AD_Reference_ID=189 AND Value='I+';
UPDATE AD_Ref_List SET ValueName='MovementFrom' WHERE AD_Reference_ID=189 AND Value='M-';
UPDATE AD_Ref_List SET ValueName='MovementTo' WHERE AD_Reference_ID=189 AND Value='M+';
UPDATE AD_Ref_List SET ValueName='ProductionPlus' WHERE AD_Reference_ID=189 AND Value='P+';
UPDATE AD_Ref_List SET ValueName='ProductionMinus' WHERE AD_Reference_ID=189 AND Value='P-';
UPDATE AD_Ref_List SET ValueName='WorkOrderPlus' WHERE AD_Reference_ID=189 AND Value='W+';
UPDATE AD_Ref_List SET ValueName='WorkOrderMinus' WHERE AD_Reference_ID=189 AND Value='W-';


-- PRIORITYRULE_
UPDATE AD_Ref_List SET ValueName='High' WHERE AD_Reference_ID=154 AND Value='3';
UPDATE AD_Ref_List SET ValueName='Medium' WHERE AD_Reference_ID=154 AND Value='5';
UPDATE AD_Ref_List SET ValueName='Low' WHERE AD_Reference_ID=154 AND Value='7';
UPDATE AD_Ref_List SET ValueName='Urgent' WHERE AD_Reference_ID=154 AND Value='1';
UPDATE AD_Ref_List SET ValueName='Minor' WHERE AD_Reference_ID=154 AND Value='9';


