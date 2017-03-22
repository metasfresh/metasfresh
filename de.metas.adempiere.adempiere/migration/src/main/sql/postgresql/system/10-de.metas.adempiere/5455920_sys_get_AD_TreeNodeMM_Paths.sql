drop function if exists public.get_AD_TreeNodeMM_Paths(numeric, numeric);
create or replace function public.get_AD_TreeNodeMM_Paths(p_AD_Tree_ID numeric, p_Root_ID numeric = null)
returns table(
	AD_Tree_ID numeric
	, Root_ID numeric
	, Node_ID numeric
	, Parent_ID numeric
	, SeqNo numeric
	, Name text
	, depth integer
	, path integer[]
	, AD_Menu_ID numeric
	, IsCycle boolean
)
as
$BODY$
--
	with recursive menu as (
		(
			SELECT
				tn.AD_Tree_ID
				, tn.Node_ID as Root_ID
				, tn.Node_ID
				, tn.Parent_ID
				, tn.SeqNo
				, coalesce(m.Name::text, tn.Node_ID::text) as Name
				, 1::integer as depth
				, ARRAY[tn.Node_ID::integer] AS path
				--, ARRAY[0] AS SeqNo_path
				, m.AD_Menu_ID -- just to see it when it's missing
				, false as IsCycle
			FROM AD_TreeNodeMM tn
			left outer join AD_Menu m on (m.AD_Menu_ID=tn.Node_ID)
			WHERE true
				--
				-- Filter AD_Tree_ID
				and ($1 is null or tn.AD_Tree_ID=$1)
				--
				-- Filter roots:
				and (case
					-- No p_Root_ID was specified => select all roots
					when $2 is null then 
						Node_ID=0
						or not exists (select 1 from AD_TreeNodeMM zz where zz.AD_Tree_ID=tn.AD_Tree_ID and zz.Node_ID=tn.Parent_ID) -- all nodes which does not really have a parent
					-- p_Root_ID was specifield => select only that root
					else
						Node_ID=$2
					end)
		)
		--
		union all
		--
		SELECT
			tn.AD_Tree_ID
			, parent.Root_ID
			, tn.Node_ID
			, tn.Parent_ID
			, tn.SeqNo
			, parent.Name || ' -> ' || coalesce(m.Name, tn.Node_ID::text) as Name
			, parent.depth + 1 as depth
			, parent.path || tn.Node_ID::integer AS path
			--, parent.SeqNo_path || tn.SeqNo::integer AS SeqNo_path
			, m.AD_Menu_ID -- just to see it when it's missing
			, (tn.Node_ID = ANY(parent.path)) as IsCycle
		FROM menu parent
		inner join AD_TreeNodeMM tn on (tn.Parent_ID=parent.Node_ID)
		left outer join AD_Menu m on (m.AD_Menu_ID=tn.Node_ID)
		where tn.AD_Tree_ID=parent.AD_Tree_ID
			and not parent.IsCycle
	)
	select * from menu
	;
$BODY$
LANGUAGE sql STABLE
COST 100;
--
COMMENT ON FUNCTION public.get_AD_TreeNodeMM_Paths(numeric, numeric) IS 'Returns a nice view with all tree nodes with their paths.';

--
-- Test it
-- select * from get_AD_TreeNodeMM_Paths(null) order by AD_Tree_ID, path

