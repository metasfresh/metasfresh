create table backup.AD_UI_Section_BKP_Before416 as select * from AD_UI_Section;

update AD_UI_Section set Name='' where Name='main';
update AD_UI_Section_Trl set Name='' where Name='main';

