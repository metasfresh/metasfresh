update AD_Process set Classname='org.compiere.report.ReportStarter' where JasperReport is not null and Classname is null;

-- select * from AD_Process where JasperReport is not null and Classname is null
