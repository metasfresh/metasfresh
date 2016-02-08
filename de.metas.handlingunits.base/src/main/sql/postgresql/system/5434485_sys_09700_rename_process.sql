
-- not directly related, but due to a refactoring, the process implementation class was touched 
--and the commit-hook script made a fuss, so why not fix it right now
UPDATE AD_Process 
SET 
	Value='Fresh_08412_ProcessHUs', 
	Name='Fresh_08412_ProcessHUs', 
	Description='See http://dewiki908/mediawiki/index.php/Fresh_08412_Auslagerung_manuelle_DD_Orders_Karotten_April_%28108521976654%29',
	ClassName='de.metas.handlingunits.maintenance.process.Fresh_08412_ProcessHUs'
WHERE AD_Process_ID=540550
;
