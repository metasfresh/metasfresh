
-- cleanup old handler records..the classes were renamed some time ago
DELETE FROM M_IolCandHandler WHERE ClassName = 'de.metas.contracts.subscription.inoutcandidate.spi.impl.SubscriptionInOutCandHandler';
DELETE FROM M_IolCandHandler WHERE ClassName = 'de.metas.inoutcandidate.spi.impl.OrderLineInOutCandHandler';


-- this sequence would be created on the next startup. but we need it now, for the insert.
CREATE SEQUENCE public.m_shipmentschedule_attributeconfig_seq
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 2147483647
  START 1000000
  CACHE 1;
ALTER TABLE public.m_shipmentschedule_attributeconfig_seq
  OWNER TO metasfresh;


--
-- insert records according to the "template" packing instruction
--
INSERT INTO m_shipmentschedule_attributeconfig (
  ad_client_id, -- numeric(10,0) NOT NULL,
  ad_org_id, -- numeric(10,0) NOT NULL,
  created, -- timestamp with time zone NOT NULL,
  createdby, -- numeric(10,0) NOT NULL,
  isactive, -- character(1) NOT NULL,
  m_attribute_id, -- numeric(10,0),
  m_iolcandhandler_id, -- numeric(10,0) NOT NULL,
  m_shipmentschedule_attributeconfig_id, -- numeric(10,0) NOT NULL,
  onlyifinreferencedasi, -- character(1) NOT NULL,
  updated, -- timestamp with time zone NOT NULL,
  updatedby -- numeric(10,0) NOT NULL,
)
select --ha.M_Attribute_ID, a.Name
  1000000 as ad_client_id, -- numeric(10,0) NOT NULL,
  0 as ad_org_id, -- numeric(10,0) NOT NULL,
  now() as created, -- timestamp with time zone NOT NULL,
  99 as createdby, -- numeric(10,0) NOT NULL,
  'Y' as isactive, -- character(1) NOT NULL,
  ha.m_attribute_id, -- numeric(10,0),
  iolch.m_iolcandhandler_id, -- numeric(10,0) NOT NULL,
  nextval('m_shipmentschedule_attributeconfig_seq') as m_shipmentschedule_attributeconfig_id, -- numeric(10,0) NOT NULL,
  'N' as onlyifinreferencedasi, -- character(1) NOT NULL,
  now() as updated, -- timestamp with time zone NOT NULL,
  99 as updatedby -- numeric(10,0) NOT NULL,
from M_HU_PI_Attribute ha
	join M_Attribute a ON a.M_Attribute_ID=ha.M_Attribute_ID and a.IsActive='Y'
	join m_iolcandhandler iolch ON true
where ha.M_HU_PI_Version_ID = 100 
	AND ha.IsActive='Y' 
	AND ha.UseInASI='Y'
	AND NOT EXISTS (select 1 from m_shipmentschedule_attributeconfig e where e.m_attribute_id=a.m_attribute_id and e.m_iolcandhandler_id=iolch.m_iolcandhandler_id)
;

