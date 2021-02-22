update ad_column set filteroperator=(case when israngefilter='Y' then 'B' else 'E' end)
where isselectioncolumn='Y'
and filteroperator is null;

