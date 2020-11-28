update AD_ModelValidator set IsActive='Y'
where ModelValidationClass='org.eevolution.model.validator.MRPInterceptor'
and AD_ModelValidator_ID=540109
and IsActive='N'
;

-- check
-- select * from AD_ModelValidator where modelValidationClass ilike '%MRPInterceptor%'
