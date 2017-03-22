insert into M_QualityNote
(ad_client_id,
ad_org_id,
created,
createdby,
isactive,
m_qualitynote_id,
name,
performancetype,
updated,
updatedby,
value )

SELECT 
 av.AD_Client_ID :: numeric(10,0) as AD_Client_ID,
 av.AD_Org_ID :: numeric(10,0) as AD_Org_ID,
  now() :: timestamp with time zone as Created,
  100 :: numeric(10,0) as CreatedBy,
  'Y' :: character(1) as IsActive,
  nextval('M_QUALITYNOTE_SEQ') :: numeric(10,0) as M_QualityNote_ID,
  av.Name :: character varying(255) as Name, 
  null :: character varying(255) as Performancetype,  
  now() :: timestamp with time zone as Updated,
  100 :: numeric(10,0) as UpdatedBy, 
  av.Value :: character varying(255) as Value
 FROM M_AttributeValue av 
 WHERE av.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = 'QualityNotice');