create table backup.AD_Process_BKP20190220_before_gh1150webui as select * from AD_Process;

update AD_Process p set ShowHelp='N'
where 
	p.ShowHelp<>'N'
	and (select count(1) from AD_Process_Para pp where pp.AD_Process_ID=p.AD_Process_ID and pp.IsActive='Y')=0;
