update ad_language set isbaselanguage='N', issystemlanguage = 'Y' where ad_language = 'de_CH';
update ad_language set isbaselanguage = 'Y', isactive = 'Y', name = 'Deutsch (Deutschland)' where ad_language = 'de_DE';

update AD_Client set ad_language = 'de_DE' where AD_Language='de_CH' and AD_Client_ID=1000000;
update C_BPartner set AD_Language='de_DE' where AD_Language='de_CH' and C_BPartner_ID in (2156423, 2155894, 2156425);

