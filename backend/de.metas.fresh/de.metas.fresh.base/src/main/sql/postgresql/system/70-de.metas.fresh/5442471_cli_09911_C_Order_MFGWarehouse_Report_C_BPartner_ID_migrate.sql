update C_Order_MFGWarehouse_Report r set C_BPartner_ID=(select o.C_BPartner_ID from C_Order o where o.C_Order_ID=r.C_Order_ID)
where C_BPartner_ID is null;

