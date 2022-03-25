
-- isdirected still exists as DB-column but is not used in the business logic anymore.
-- we need to deactivate the former bi-directional RTs because many or all don'T work and clutter the log with errors
update ad_relationtype set isactive='N', updated='2021-09-23 06:25', updatedby=99 where isdirected='N' and isactive='Y';

-- although, this old relation-type does work and is in fact needed 
update ad_relationtype set isactive='Y', isdirected='N' where internalname='C_OLCandProcessor_1000003<=>C_OLCand';
