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
  
  , QualityInspectionCycle_Attribute_id numeric(10)
  , QualityInspectionCycle_Value character varying(255)
  , QualityInspectionCycle_Name character varying(60)
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
		elsif (v_hua.AttributeCode = '1000020') then --Zitrusfr�chte 
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
		--
		-- QualityInspectionCycle
		elsif (v_hua.AttributeCode = 'QualityInspectionCycle') then
			v_result.QualityInspectionCycle_Attribute_id = v_hua.M_Attribute_ID;
			v_result.QualityInspectionCycle_Value = v_hua.Value;
			v_result.QualityInspectionCycle_Name = v_hua.ValueName;
		end if;
	end loop;
	
	
	return v_result;
end;
$$
LANGUAGE plpgsql STABLE;


-- TEST:
--select "de.metas.handlingunits".getHUAttributes(1000083);
