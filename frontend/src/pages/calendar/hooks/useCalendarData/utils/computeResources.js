import { updateResourcesFromConflicts } from './updateResourcesFromConflicts';
import { removeZeroEntriesResourcesFromArray } from './removeZeroEntriesResourcesFromArray';

export const computeResources = ({ calendars, entries, conflicts }) => {
  if (!calendars) {
    return [];
  }

  let resources = calendars.flatMap((calendar) => calendar.resources);
  if (resources.length === 0) {
    return [];
  }

  resources = updateResourcesFromConflicts({ resources, entries, conflicts });
  resources = removeZeroEntriesResourcesFromArray(resources);

  return resources;
};
