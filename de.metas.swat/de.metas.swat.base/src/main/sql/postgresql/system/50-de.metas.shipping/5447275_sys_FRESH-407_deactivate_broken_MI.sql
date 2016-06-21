
UPDATE AD_ModelValidator SET IsActive='N', Description='We currently don''t need this MV and it does not guard against NPE' WHERE ModelValidationClass='de.metas.shipping.model.validator.ShipperTransportationMailNotification';
