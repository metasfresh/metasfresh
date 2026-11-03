import { useQuery } from '../../../../hooks/useQuery';
import { countLaunchers } from '../../../../api/launchers';
import { computeActiveFacetIdsFromGroups } from '../utils';

export const useResultsCount = ({ applicationId, groupsLoading, groups, filters }) => {
  const facetIds = computeActiveFacetIdsFromGroups(groups);
  const { isPending, data } = useQuery({
    enabled: !groupsLoading,
    queryKey: [applicationId, filters, facetIds.join('|')],
    queryFn: () => {
      return countLaunchers({
        applicationId,
        facetIds,
        filters,
      });
    },
  });

  return { resultsCountLoading: isPending, resultsCount: data };
};
