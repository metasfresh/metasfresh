
DROP INDEX IF EXISTS m_product_value;
CREATE UNIQUE INDEX m_product_value
    ON public.m_product USING btree
    (Value, AD_Org_ID, AD_Client_ID);
    