drop function if exists "de.metas.handlingunits".getHUAttributes(p_M_HU_ID numeric);
drop function if exists "de.metas.handlingunits".getHUAttributes(p_M_HU_ID numeric, p_FillPurchaseDocInfo boolean);
drop type if exists "de.metas.handlingunits".HUAttributes;

CREATE TYPE "de.metas.handlingunits".HUAttributes AS
(
  m_hu_id numeric(10,0),
  subproducerbpartner_attribute_id numeric(10,0),
  subproducerbpartner_value numeric,
  herkunft_attribute_id numeric(10,0),
  herkunft_value character varying(255),
  herkunft_name character varying(60),
  lotnumber_attribute_id numeric(10,0),
  lotnumber_value character varying(255),
  label_attribute_id numeric(10,0),
  label_value character varying(255),
  label_name character varying(60),
  produktionsart_attribute_id numeric(10,0),
  produktionsart_value character varying(255),
  produktionsart_name character varying(60),
  adr_m_attribute_id numeric(10,0),
  adr_value character varying(255),
  adr_name character varying(60),
  qualitydiscountpercent_attribute_id numeric(10,0),
  qualitydiscountpercent_value numeric,
  qualitynotice_attribute_id numeric(10,0),
  qualitynotice_value character varying(255),
  qualitynotice_name character varying(60),
  zitrus_attribute_id numeric(10,0),
  zitrus_value character varying(255),
  zitrus_name character varying(60),
  materialtracking_attribute_id numeric(10,0),
  materialtracking_value numeric(10,0),
  mhd_attribute_id numeric(10,0),
  mhd_value character varying(255),
  lotnumberdate_m_attribute_id numeric(10,0),
  lotnumberdate_value timestamp without time zone
  --
  , PurchaseOrderLine_Attribute_ID numeric(10)
  , PurchaseOrderLine_Value numeric(10)
  --
  , ReceiptInOutLine_Attribute_ID numeric(10)
  , ReceiptInOutLine_Value numeric(10)
  --
  , isQualityInspection_Attribute_ID numeric(10)
  , isQualityInspection_Value character varying(255)
);

create or replace function "de.metas.handlingunits".getHUAttributes(p_M_HU_ID numeric, p_FillPurchaseDocInfo boolean default false)
returns "de.metas.handlingunits".HUAttributes
as $$
declare
	v_hua record;
	v_result "de.metas.handlingunits".HUAttributes;
begin
	v_result.M_HU_ID := p_M_HU_ID;
	
	for v_hua in (
		select
			hua.M_Attribute_ID
			, a.Value as AttributeCode
			, hua.Value, hua.ValueNumber, hua.ValueDate
			, av.Name as ValueName
		from M_HU_Attribute hua
		inner join M_Attribute a on (a.M_Attribute_ID=hua.M_Attribute_ID)
		left join M_AttributeValue av on (av.M_Attribute_ID=a.M_Attribute_ID and av.Value=hua.Value)
		where hua.M_HU_ID=p_M_HU_ID
	)
	loop
		-- raise notice 'Attribute: %', v_hua.AttributeCode;
		
		if (v_hua.AttributeCode = 'SubProducerBPartner') then
			v_result.SubProducerBPartner_Attribute_ID = v_hua.M_Attribute_ID;
			v_result.SubProducerBPartner_Value = v_hua.ValueNumber;
		elsif (v_hua.AttributeCode = '1000001') then --herkunft
			v_result.Herkunft_Attribute_ID = v_hua.M_Attribute_ID;
			v_result.Herkunft_Value = v_hua.Value;
			v_result.Herkunft_Name = v_hua.ValueName;
		elsif (v_hua.AttributeCode = 'Lot-Nummer') then
			v_result.lotnumber_attribute_id = v_hua.M_Attribute_ID;
			v_result.lotnumber_value = v_hua.Value;
		elsif (v_hua.AttributeCode = '1000002') then --label
			v_result.label_attribute_id = v_hua.M_Attribute_ID;
			v_result.label_value = v_hua.Value;
			v_result.label_name = v_hua.ValueName;
		elsif (v_hua.AttributeCode = '1000004') then --Produktionsart
			v_result.produktionsart_attribute_id = v_hua.M_Attribute_ID;
			v_result.produktionsart_value = v_hua.Value;
			v_result.produktionsart_name = v_hua.ValueName;
		elsif (v_hua.AttributeCode = '1000015') then --Adr
			v_result.adr_m_attribute_id = v_hua.M_Attribute_ID;
			v_result.adr_value = v_hua.Value;
			v_result.adr_name = v_hua.ValueName;
		elsif (v_hua.AttributeCode = 'QualityDiscountPercent') then
			v_result.qualitydiscountpercent_attribute_id = v_hua.M_Attribute_ID;
			v_result.qualitydiscountpercent_value = v_hua.ValueNumber;
		elsif (v_hua.AttributeCode = 'QualityNotice') then
			v_result.qualitynotice_attribute_id = v_hua.M_Attribute_ID;
			v_result.qualitynotice_value = v_hua.Value;
			v_result.qualitynotice_name = v_hua.ValueName;
		elsif (v_hua.AttributeCode = '1000020') then --Zitrusfr?chte 
			v_result.Zitrus_Attribute_ID = v_hua.M_Attribute_ID;
			v_result.Zitrus_Value = v_hua.Value;
			v_result.Zitrus_Name = v_hua.ValueName;
		elsif (v_hua.AttributeCode = 'M_Material_Tracking_ID') then
			v_result.materialtracking_attribute_id = v_hua.M_Attribute_ID;
			v_result.materialtracking_value = v_hua.Value::numeric;		
		elsif (v_hua.AttributeCode = '1000021') then --MHD
			v_result.mhd_attribute_id = v_hua.M_Attribute_ID;
			v_result.mhd_value = v_hua.Value;	
		elsif (v_hua.AttributeCode = 'HU_LotNumberDate') then 
			v_result.lotnumberdate_m_attribute_id = v_hua.M_Attribute_ID;
			v_result.lotnumberdate_value = v_hua.ValueDate;	
		--
		-- Purchase order line ID
		elsif(v_hua.AttributeCode = 'HU_PurchaseOrderLine_ID') then
			v_result.PurchaseOrderLine_Attribute_ID = v_hua.M_Attribute_ID;
			v_result.PurchaseOrderLine_Value = v_hua.ValueNumber;	
		--
		-- ReceiptInOutLine
		elsif(v_hua.AttributeCode = 'HU_ReceiptInOutLine_ID') then
			v_result.ReceiptInOutLine_Attribute_ID = v_hua.M_Attribute_ID;
			v_result.ReceiptInOutLine_Value = v_hua.ValueNumber;	
		
		--
		-- isQualityInspection
		elsif (v_hua.AttributeCode = 'IsQualityInspection') then
			v_result.isQualityInspection_Attribute_ID = v_hua.M_Attribute_ID;
			v_result.isQualityInspection_Value = v_hua.Value;
		end if;
	end loop;
	
	
	return v_result;
end;
$$
LANGUAGE plpgsql STABLE;


-- TEST:
--select "de.metas.handlingunits".getHUAttributes(1000083);





DROP FUNCTION IF EXISTS report.receipt_label(IN M_HU_ID numeric);

CREATE FUNCTION report.receipt_label(IN M_HU_ID numeric) RETURNS TABLE
	(
	vendoraddress text, 
	vendorgap Character Varying, 
	produceraddress text,
	producergap Character Varying,
	Herkunft Character Varying,
	Charge Character Varying,
	Label Character Varying,
	Anbau Character Varying,
	LotNumberDate Character Varying,
	orderdocNo Character Varying,
	p_name Character Varying,
	adr Character Varying,
	receiptdate Date,
	Beurteilung numeric,
	notiz Character Varying,
	zusatz Character Varying,
	tracking Character Varying,
	mhd Character Varying,
	receiptdocno Character Varying,
	PackingInstruction Character Varying,
	deliverydate timestamp without time zone,
	isQualityInspection Character Varying
	)
AS 
$$

SELECT distinct
(SELECT DISTINCT Regexp_Replace(o.BPartnerAddress, E'\n', ', ', 'g')
		FROM C_OrderLine ol
		INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
		WHERE ol.C_OrderLine_ID = (t.TU_Attrs).PurchaseOrderLine_Value
)  AS vendoraddress

  ,vendorbp.naics AS vendorgap
 	,(
		SELECT	COALESCE(bp.name||', ', '') || Regexp_Replace(address, E'\n', ', ', 'g')
		FROM	C_BPartner bp
			INNER JOIN C_BPartner_Location bpl ON bp.C_BPartner_ID = bpl.C_BPartner_ID
		WHERE	bpl.C_BPartner_ID =  (t.TU_Attrs).SubProducerBPartner_Value
		LIMIT 1
	) AS produceraddress
	,producerbp.naics AS producergap
 , (t.TU_Attrs).herkunft_name AS Herkunft
 , (t.TU_Attrs).lotnumber_value AS Charge
  ,(t.TU_Attrs).label_name AS Label
  ,(t.TU_Attrs).produktionsart_name AS Anbau
   ,"de.metas.handlingunits".HU_LotNumberDate_ToString((t.TU_Attrs).lotnumberdate_value::date) AS LotNumberDate
  ,(
		SELECT DISTINCT o.documentno
		FROM C_OrderLine ol
		INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
		WHERE ol.C_OrderLine_ID = (t.TU_Attrs).PurchaseOrderLine_Value
		LIMIT 1
	) AS orderdocNo
  ,p.name AS p_name
  ,CASE WHEN (t.TU_Attrs).adr_value ='01' THEN NULL ELSE (t.TU_Attrs).adr_name END AS adr
  ,(SELECT created FROM M_HU WHERE M_HU_ID=TU_HU_ID)::date AS receiptdate
  ,(t.TU_Attrs).qualitydiscountpercent_value AS Beurteilung
  ,(t.TU_Attrs).qualitynotice_name AS notiz
  ,(t.TU_Attrs).zitrus_name AS zusatz
  ,tr.lot AS tracking
  ,(t.TU_Attrs).mhd_value AS mhd
  ,COALESCE(luvalue,tuvalue) AS receiptdocno
  ,hu_piip.Name AS PackingInstruction

	,(
		SELECT DISTINCT io.movementdate
		FROM M_InOutLine iol
		INNER JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
		WHERE iol.M_InOutLine_ID=(t.TU_Attrs).ReceiptInOutLine_Value
	) AS deliverydate
	, (t.TU_Attrs).isQualityInspection_Value AS isQualityInspection
FROM (
 SELECT
  lu.M_HU_ID as LU_HU_ID
  , tu.M_HU_ID as TU_HU_ID
  , lu.value as luvalue, tu.value as tuvalue
  , "de.metas.handlingunits".getHUAttributes(tu.M_HU_ID, p_FillPurchaseDocInfo := true) as TU_Attrs
  ,lu.C_BPartner_ID as C_BPartner_ID
 FROM
 "de.metas.handlingunits".get_All_TUs($1) tu
 left outer join M_HU_Item lui on (lui.M_HU_Item_ID=tu.M_HU_Item_Parent_ID)
 left outer join M_HU lu on (lu.M_HU_ID=lui.M_HU_ID)
) t
LEFT OUTER JOIN C_BPartner vendorbp ON (SELECT C_BPartner_ID FROM M_HU WHERE M_HU_ID = TU_HU_ID) =vendorbp.C_BPartner_ID
LEFT OUTER JOIN C_BPartner producerbp ON (t.TU_Attrs).SubProducerBPartner_Value = producerbp.C_BPartner_ID
INNER JOIN M_HU_Storage lus ON TU_HU_ID = lus.M_HU_ID
INNER JOIN M_Product p ON lus.M_product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Material_Tracking tr ON (t.TU_Attrs).materialtracking_value = tr.M_Material_Tracking_ID
LEFT OUTER JOIN M_HU_PI_Item_Product hu_piip ON hu_piip.M_HU_PI_Item_Product_ID = (SELECT M_HU_PI_Item_Product_ID FROM M_HU WHERE M_HU_ID=TU_HU_ID)


$$
  LANGUAGE sql STABLE