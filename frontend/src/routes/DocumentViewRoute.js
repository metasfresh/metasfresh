import React from 'react';
import PropTypes from 'prop-types';
import queryString from 'query-string';
import { queriesAreEqual } from './utils';
import DocumentListContainerWrapper from '../containers/DocumentListContainerWrapper';

const DocumentViewRoute = ({ location, match }) => {
  const { search, pathname } = location;
  const { params } = match;
  const query = queryString.parse(search, {
    ignoreQueryPrefix: true,
  });

  return (
    <DocumentListContainerWrapper
      query={query}
      windowId={params.windowId}
      pathname={pathname}
    />
  );
};

DocumentViewRoute.propTypes = {
  location: PropTypes.object,
  match: PropTypes.object,
};

export default React.memo(DocumentViewRoute, queriesAreEqual);
