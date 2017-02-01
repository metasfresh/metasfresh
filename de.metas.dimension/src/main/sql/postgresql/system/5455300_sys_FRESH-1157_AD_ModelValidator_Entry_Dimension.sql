INSERT INTO AD_ModelValidator
(
  ad_client_id ,
  ad_modelvalidator_id ,
  ad_org_id ,
  created ,
  createdby ,
  updated ,
  updatedby ,
  isactive ,
  name ,
  description ,
  help ,
  entitytype ,
  modelvalidationclass ,
  seqno 
)
VALUES
(
	99 --as ad_client_id 
	,nextval('c_bp_bankaccount_id'::regclass) --as  ad_modelvalidator_id 
	,0 --as ad_org_id 
	,now() --as created
	,99 --as createdby 
	,now() --as updated 
	,99 --as updatedby 
	,'Y' --as isactive 
	,'dimensionMainValidator' --as name 
	,null --as description 
	,null --as help 
	,'de.metas.dimension' --as entitytype character varying(40) NOT NULL
	,'de.metas.dimension.model.validator.Main' --as modelvalidationclass 
	,0 --as seqno 
 
);