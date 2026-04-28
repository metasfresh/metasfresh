import _ from 'lodash';

export const queriesAreEqual = (prevProps, nextProps) => {
  const { match, location } = prevProps;
  const { match: nextMatch, location: nextLocation } = nextProps;

  return (
    _.isEqual(location.query, nextLocation.query) &&
    match.params.windowId === nextMatch.params.windowId
  );
};
