
create unique index UNQ_FEC_DocumentNo on c_foreignexchangecontract(documentno, ad_org_id) where isActive='Y'
;