-- 18.09.2015 19:33
-- Customize the "Standard report"
UPDATE AD_PrintTableFormat SET 
	HdrTextBG_PrintColor_ID=NULL
	, HdrStroke=1, LineStroke=1
	, HdrTextFG_PrintColor_ID=117
	, HdrLine_PrintColor_ID=117
	,Updated=TO_TIMESTAMP('2015-09-18 19:33:02','YYYY-MM-DD HH24:MI:SS')
	,UpdatedBy=100
WHERE AD_PrintTableFormat_ID=100
;

-- Change default font from SansSerif11 to SansSerif9
update AD_PrintFont set IsDefault='N' where IsDefault='Y';
update AD_PrintFont set IsDefault='Y' where Name='SansSerif 9';


-- Print format: change font from SansSerif11 to SansSerif9
update AD_PrintFormat set AD_PrintFont_ID=163 where AD_PrintFont_ID=130;

-- Change "Letter Landscape" to "A4 Landscape"
update AD_PrintFormat set AD_PrintPaper_ID=102 where AD_PrintPaper_ID=100;
-- Change "Letter Portrait" to "A4 Portrait"
update AD_PrintFormat set AD_PrintPaper_ID=103 where AD_PrintPaper_ID=101;
