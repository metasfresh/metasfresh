Create table backup.BKP_ModCntr_Module_22042024 as select * from modcntr_module;


UPDATE modcntr_module set invoicinggroup = (case when invoicinggroup = 'Kosten' then 'Costs' else 'Service' end) WHERE invoicinggroup not in ('Costs', 'Service');
