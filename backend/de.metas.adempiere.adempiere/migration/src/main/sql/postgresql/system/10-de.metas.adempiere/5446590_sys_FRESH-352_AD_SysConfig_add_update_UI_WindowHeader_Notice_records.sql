
-- 30.05.2016 13:04
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Notice to be displayed on all UI windows. Empty or just the - sign means "no notice".
See http://dewiki908/mediawiki/index.php/Ti54_05730_Use_different_Theme_colour_on_UAT_system', Name='UI_WindowHeader_Notice_Text',Updated=TO_TIMESTAMP('2016-05-30 13:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50083
;

-- 30.05.2016 13:11
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540990,'C',TO_TIMESTAMP('2016-05-30 13:11:14','YYYY-MM-DD HH24:MI:SS'),100,'If UI_WindowHeader_Notice_Text is set, this value can speificy the background color. If no text is set or if the value string can''t be parsed, then this is ignored.
If just a text, but no valid color is set, then the background is red.
Valid values are "html colors". Examples: #ff0000 => red; #00ff00 is green; #0000ff is blue; #ffff00 is yellow;
Hint: see http://www.color-hex.com/

Issue https://metasfresh.atlassian.net/browse/FRESH-352','D','Y','UI_WindowHeader_Notice_BG_Color',TO_TIMESTAMP('2016-05-30 13:11:14','YYYY-MM-DD HH24:MI:SS'),100,'#ffff00')
;

-- May 30, 2016 1:18 PM
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='If UI_WindowHeader_Notice_Text is set, this value can speificy the background color. If no text is set or if the value string can''t be parsed, then this is ignored.
If just a text, but no valid color is set, then the background is red.
Valid values are "html colors". Examples: #ff0000 => red; #00ff00 is green; #0000ff is blue; #ffff00 is yellow; #ffffff is white; #000000 is black
Hint: also see http://www.color-hex.com/ or http://html-color-codes.info/

Issue https://metasfresh.atlassian.net/browse/FRESH-352',Updated=TO_TIMESTAMP('2016-05-30 13:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540990
;

-- May 30, 2016 1:19 PM
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540991,'C',TO_TIMESTAMP('2016-05-30 13:19:18','YYYY-MM-DD HH24:MI:SS'),100,'Foreground color for windows header notice. If not set, white is used as default. See UI_WindowHeader_Notice_BG_Color for further infos.','D','Y','UI_WindowHeader_Notice_FG_Color',TO_TIMESTAMP('2016-05-30 13:19:18','YYYY-MM-DD HH24:MI:SS'),100,'#000000')
;


-- May 30, 2016 1:33 PM
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2016-05-30 13:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540990
;

-- May 30, 2016 1:33 PM
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2016-05-30 13:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540991
;

-- May 30, 2016 1:33 PM
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2016-05-30 13:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50083
;

-- May 30, 2016 1:33 PM
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='If UI_WindowHeader_Notice_Text is set, this value can speficy the background color. If no text is set or if the value-string can''t be parsed, then this is ignored.
If just a text, but no valid color is set, then the background is red.
Valid values are "html colors". Examples: #ff0000 is red; #00ff00 is green; #0000ff is blue; #ffff00 is yellow; #ffffff is white; #000000 is black
Hint: also see http://www.color-hex.com/ or http://html-color-codes.info/

Issue https://metasfresh.atlassian.net/browse/FRESH-352',Updated=TO_TIMESTAMP('2016-05-30 13:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540990
;

