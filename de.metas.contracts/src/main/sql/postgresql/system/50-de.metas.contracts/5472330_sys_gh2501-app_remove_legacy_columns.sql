
--
-- Remove C_SubscriptionProgress.IsSubScriptionConfirmed
--
DELETE FROM AD_UI_Element WHERE AD_FIELD_ID IN (SELECt AD_Field_ID FROM AD_FIELD WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540086));

DELETE FROM AD_FIELD WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540086);

DELETE FROM AD_Column WHERE AD_Element_ID=540086;

DELETE FROM AD_Element WHERE AD_Element_ID=540086;

-- delete status "waits-for-confirmation"
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=540005
;
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=540005
;


SELECT public.db_alter_table('C_SubscriptionProgress', 'ALTER TABLE public.C_SubscriptionProgress DROP COLUMN IsSubScriptionConfirmed;');

--
-- remove C_BPartner.IsSubscriptionconfirmRequired
--
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (select AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540085));

DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540085);

DELETE FROM EXP_FormatLine WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540085);

DELETE FROM AD_Column WHERE AD_Element_ID=540085;

DELETE FROM AD_Element WHERE AD_Element_ID=540085;

SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE public.C_BPartner DROP COLUMN IsSubscriptionconfirmRequired;');

--
--Remove C_BPartner.PostageFree
--
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (select AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540637));

DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540637);

DELETE FROM EXP_FormatLine WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540637);

DELETE FROM AD_Column WHERE AD_Element_ID=540637;

DELETE FROM AD_Element WHERE AD_Element_ID=540637;

DELETE FROM AD_Ref_List WHERE AD_Reference_ID=540155;
DELETE FROM AD_Reference WHERE AD_Reference_ID=540155;

SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE public.C_BPartner DROP COLUMN PostageFree;');


--
-- Remove C_Flatrate_Term.IsPostageFree
--
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (select AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540638));

DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540638);

DELETE FROM EXP_FormatLine WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=540638);

DELETE FROM AD_Column WHERE AD_Element_ID=540638;

DELETE FROM AD_Element WHERE AD_Element_ID=540638;

SELECT public.db_alter_table('C_Flatrate_Term', 'ALTER TABLE public.C_Flatrate_Term DROP COLUMN IsPostageFree;');



--
-- remove PostageFreeAmt aka "Porto Freibetrag", because it's impelmentation has a bad performance and we don't need it now
--
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (select AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=500056));

DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=500056);

DELETE FROM EXP_FormatLine WHERE AD_Column_ID IN (select AD_Column_ID from AD_Column where AD_Element_ID=500056);

DELETE FROM AD_Column WHERE AD_Element_ID=500056;

DELETE FROM AD_Element WHERE AD_Element_ID=500056;


DROP VIEW "public".c_orderline_id_with_missing_shipmentschedule_v;

SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE public.C_BPartner DROP COLUMN PostageFreeAmt;');

SELECT public.db_alter_table('M_ShipmentSchedule', 'ALTER TABLE public.M_ShipmentSchedule DROP COLUMN PostageFreeAmt;');

CREATE OR REPLACE VIEW public.c_orderline_id_with_missing_shipmentschedule_v AS 
SELECT ol.c_orderline_id
FROM c_orderline ol
     JOIN c_order o ON ol.c_order_id = o.c_order_id
     JOIN c_doctype dt ON dt.c_doctype_id = o.c_doctype_id
     JOIN m_product p ON p.m_product_id = ol.m_product_id AND p.producttype = 'I'::bpchar
WHERE true 
	AND ol.qtyordered <> ol.qtydelivered 
	AND NOT EXISTS ( SELECT 1 FROM m_shipmentschedule s	WHERE s.c_orderline_id = ol.c_orderline_id)
	AND dt.docbasetype = 'SOO'::bpchar 
	AND (dt.docsubtype <> ALL (ARRAY['ON'::bpchar, 'OB'::bpchar, 'WR'::bpchar])) 
	AND o.issotrx = 'Y'::bpchar 
	AND o.docstatus = 'CO'::bpchar 
	AND NOT EXISTS ( 
				SELECT 1 
				FROM m_iolcandhandler_log log
				WHERE log.m_iolcandhandler_id = 1000000::numeric AND log.ad_table_id = 260::numeric AND log.record_id = ol.c_orderline_id AND log.isactive = 'Y'::bpchar
			);

ALTER TABLE public.c_orderline_id_with_missing_shipmentschedule_v OWNER TO metasfresh;
COMMENT ON VIEW public.c_orderline_id_with_missing_shipmentschedule_v
  IS 'Selects C_OrderLines that should have a shipment schedule and do not have one yet.
Issue gh #992: 
  * Only consider C_OrderLines whose product type is "I" (Item). Please keep this in sync with IProductBL.isItem() and IProduct.isService(). 
  * Note that the delivery rule for invoice candidates is already set to "Immediate" for such products.
Also see task 08896';