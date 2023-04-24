DO $$
BEGIN
    IF exists(select 1 from ad_column where ad_column_id = 572811 and columnname = 'SalesRep_ID' and ColumnSQL != '(select o.SalesRep_ID from C_Order o where o.C_Order_ID = C_Invoice_Candidate.C_Order_ID)') THEN
        update ad_column SET ColumnSQL = '(select o.SalesRep_ID from C_Order o where o.C_Order_ID = C_Invoice_Candidate.C_Order_ID)', updated = now() where ad_column_id = 572811;
    END IF;
END $$;