INSERT INTO c_conversiontype_default
(
	ad_client_id, ad_org_id
	, validfrom
	, c_conversiontype_id
	, c_conversiontype_default_id
	, created, createdby, isactive, updated, updatedby
)
select
	ad_client_id, ad_org_id
	, '1970-01-01'::date
	, c_conversiontype_id
	, nextval('c_conversiontype_default_seq')
	, now(), 0, 'Y', now(), 0
from C_ConversionType
where IsDefault='Y'
;

-- select * from c_conversiontype_default;

