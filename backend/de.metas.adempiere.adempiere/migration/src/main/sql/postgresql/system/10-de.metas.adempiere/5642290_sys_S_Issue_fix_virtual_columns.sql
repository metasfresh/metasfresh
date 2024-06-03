-- Column: S_Issue.Internal_status
-- 2022-06-06T12:25:59.032Z
UPDATE AD_Column SET ColumnSQL='( select child.status from s_issue child where child.iseffortissue = ''Y'' and child.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'')', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571096
;

-- Column: S_Issue.InternalApproved
-- Column SQL (old): ( select effortChild.isapproved from s_issue effortChild where effortChild.iseffortissue = 'Y'  and effortChild.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:31:29.295Z
UPDATE AD_Column SET ColumnSQL='( select effortChild.isapproved from s_issue effortChild where effortChild.iseffortissue = ''Y''  and effortChild.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570802
;

-- Column: S_Issue.InternalDeliveredDate
-- Column SQL (old): ( select effortChild.deliveredDate from s_issue effortChild where effortChild.iseffortissue = 'Y'  and effortChild.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:32:16.208Z
UPDATE AD_Column SET ColumnSQL='( select effortChild.deliveredDate from s_issue effortChild where effortChild.iseffortissue = ''Y''  and effortChild.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571844
;

-- Column: S_Issue.hasInternalEffortIssue
-- Column SQL (old): ( case when  exists (select 1 from s_issue child where child.iseffortissue = 'Y' and child.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N') then 'Y' else 'N' end )
-- 2022-06-06T13:34:18.623Z
UPDATE AD_Column SET ColumnSQL='( case when  exists (select 1 from s_issue child where child.iseffortissue = ''Y'' and child.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'') then ''Y'' else ''N'' end )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570683
;

-- Column: S_Issue.Internal_Assignee_ID
-- Column SQL (old): ( select child.ad_user_id         from s_issue child         where child.iseffortissue = 'Y'           and child.s_parent_issue_id=s_issue.s_issue_id           and s_issue.iseffortissue = 'N'         )
-- 2022-06-06T13:34:31.579Z
UPDATE AD_Column SET ColumnSQL='( select child.ad_user_id         from s_issue child         where child.iseffortissue = ''Y''           and child.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id           and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N''         )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570686
;

-- Column: S_Issue.Internal_Budgeted
-- Column SQL (old): ( select effortChild.budgetedeffort from s_issue effortChild where effortChild.iseffortissue = 'Y'  and effortChild.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:34:45.794Z
UPDATE AD_Column SET ColumnSQL='( select effortChild.budgetedeffort from s_issue effortChild where effortChild.iseffortissue = ''Y''  and effortChild.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:34:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570800
;

-- Column: S_Issue.Internal_DueDate
-- Column SQL (old): (select sm.milestone_duedate from s_issue effortChild     inner join s_milestone sm on effortChild.s_milestone_id = sm.s_milestone_id where effortChild.iseffortissue = 'Y'   and effortChild.s_parent_issue_id = s_issue.s_issue_id   and s_issue.iseffortissue = 'N')
-- 2022-06-06T13:35:00.071Z
UPDATE AD_Column SET ColumnSQL='(select sm.milestone_duedate from s_issue effortChild     inner join s_milestone sm on effortChild.s_milestone_id = sm.s_milestone_id where effortChild.iseffortissue = ''Y''   and effortChild.s_parent_issue_id = @JoinTableNameOrAliasIncludingDot@s_issue_id   and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'')', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570796
;

-- Column: S_Issue.Internal_Effort_S_Issue_ID
-- Column SQL (old): ( select child.s_issue_id         from s_issue child         where child.iseffortissue = 'Y'           and child.s_parent_issue_id=s_issue.s_issue_id  and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:35:12.217Z
UPDATE AD_Column SET ColumnSQL='( select child.s_issue_id         from s_issue child         where child.iseffortissue = ''Y''           and child.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id  and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:35:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570685
;

-- Column: S_Issue.Internal_EstimatedEffort
-- Column SQL (old): ( select effortChild.estimatedeffort from s_issue effortChild where effortChild.iseffortissue = 'Y'  and effortChild.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:35:23.979Z
UPDATE AD_Column SET ColumnSQL='( select effortChild.estimatedeffort from s_issue effortChild where effortChild.iseffortissue = ''Y''  and effortChild.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570797
;

-- Column: S_Issue.Internal_PlannedUATDate
-- Column SQL (old): ( select effortChild.planneduatdate from s_issue effortChild where effortChild.iseffortissue = 'Y'  and effortChild.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:35:41.115Z
UPDATE AD_Column SET ColumnSQL='( select effortChild.planneduatdate from s_issue effortChild where effortChild.iseffortissue = ''Y''  and effortChild.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:35:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570798
;

-- Column: S_Issue.Internal_processed
-- Column SQL (old): ( select child.processed          from s_issue child          where child.iseffortissue = 'Y'            and child.s_parent_issue_id=s_issue.s_issue_id            and s_issue.iseffortissue = 'N')
-- 2022-06-06T13:35:59.464Z
UPDATE AD_Column SET ColumnSQL='( select child.processed          from s_issue child          where child.iseffortissue = ''Y''            and child.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id            and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'')', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571097
;

-- Column: S_Issue.Internal_RoughEstimation
-- Column SQL (old): ( select effortChild.roughestimation from s_issue effortChild where effortChild.iseffortissue = 'Y'  and effortChild.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:36:15.995Z
UPDATE AD_Column SET ColumnSQL='( select effortChild.roughestimation from s_issue effortChild where effortChild.iseffortissue = ''Y''  and effortChild.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570799
;

-- Column: S_Issue.Internal_S_Milestone_ID
-- Column SQL (old): ( select effortChild.s_milestone_id from s_issue effortChild where effortChild.iseffortissue = 'Y'  and effortChild.s_parent_issue_id=s_issue.s_issue_id and s_issue.iseffortissue = 'N' )
-- 2022-06-06T13:36:57.737Z
UPDATE AD_Column SET ColumnSQL='( select effortChild.s_milestone_id from s_issue effortChild where effortChild.iseffortissue = ''Y''  and effortChild.s_parent_issue_id=@JoinTableNameOrAliasIncludingDot@s_issue_id and @JoinTableNameOrAliasIncludingDot@iseffortissue = ''N'' )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570795
;

-- Column: S_Issue.Milestone_DueDate
-- Column SQL (old): ( select s_milestone.milestone_duedate    from s_milestone     where s_milestone.s_milestone_id = s_issue.s_milestone_Id )
-- 2022-06-06T13:58:08.211Z
UPDATE AD_Column SET ColumnSQL='( select s_milestone.milestone_duedate    from s_milestone     where s_milestone.s_milestone_id = S_Issue.s_milestone_Id )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 16:58:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570622
;

