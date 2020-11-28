ALTER TABLE c_bpartner_product DROP CONSTRAINT c_bpartner_product_pkey;
ALTER TABLE c_bpartner_product
ADD CONSTRAINT c_bpartner_product_pkey PRIMARY KEY(c_bpartner_id, m_product_id, ad_org_id);