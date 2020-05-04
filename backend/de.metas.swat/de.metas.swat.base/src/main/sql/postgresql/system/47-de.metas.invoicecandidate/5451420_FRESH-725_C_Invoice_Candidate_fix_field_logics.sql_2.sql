-- Sep 28, 2016 10:05 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='((@QtyToInvoice_Override/0@=0 | (@QtyToInvoice_Override/-1@=-1 & @QtyToInvoice/0@=0)) & @QtyOrdered/0@!0) | @IsToClear/N@=Y | @Processed/N@=Y | @IsError/N@=Y | @IsInDispute/N@=Y',Updated=TO_TIMESTAMP('2016-09-28 22:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551293
;

