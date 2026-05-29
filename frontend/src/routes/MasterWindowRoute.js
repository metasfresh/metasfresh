import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';
import _ from 'lodash';
import { createWindow } from '../actions/WindowActions';
import MasterWindowContainer from '../containers/MasterWindowContainer';

const MasterWindowRoute = (props) => {
  const dispatch = useDispatch();
  const {
    match: { params: matchParams },
    location: { search: urlSearchParams },
  } = props;

  const { windowId, docId } = matchParams;

  useEffect(() => {
    dispatch(createWindow({ windowId, docId, urlSearchParams }));
  }, [windowId, docId, urlSearchParams]);

  return <MasterWindowContainer {...props} params={matchParams} />;
};

MasterWindowRoute.propTypes = {
  location: PropTypes.object,
  match: PropTypes.object,
};

const propsAreEqual = (prevProps, nextProps) => {
  const { match, location } = prevProps;
  const { match: nextMatch, location: nextLocation } = nextProps;

  return _.isEqual(match, nextMatch) && _.isEqual(location, nextLocation);
};

export default React.memo(MasterWindowRoute, propsAreEqual);
