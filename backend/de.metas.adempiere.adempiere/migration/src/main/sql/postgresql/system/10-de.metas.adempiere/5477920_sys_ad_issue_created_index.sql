
CREATE INDEX IF NOT EXISTS ad_issue_created
  ON public.ad_issue
  USING btree
  (created); 
COMMENT ON INDEX public.ad_issue_created
  IS 'Aims to improve the performance of selects with "where created between somedate and someotherdate".
Example:
select * from ad_issue where created between ''2017-11-21 09:00'' and ''2017-11-21 09:30'' and issuesummary ilike ''%HasChanges%''';


