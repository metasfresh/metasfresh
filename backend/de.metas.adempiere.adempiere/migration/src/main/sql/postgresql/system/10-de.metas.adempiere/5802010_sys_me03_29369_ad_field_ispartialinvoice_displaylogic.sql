-- Fix #7 on PR #23780: Restrict IsPartialInvoice AD_Field visibility to DocBaseType=API
-- Only show the "Teilrechnung" field in DocType window when the document's BaseType is "API" (Vendor Invoice)
UPDATE AD_Field
SET DisplayLogic='@DocBaseType/X@=''API'''
WHERE AD_Field_ID=777445 /*IsPartialInvoice (Teilrechnung)*/;
