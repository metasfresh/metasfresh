import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import CommentsPanelListingItem from './CommentsPanelListingItem';

export const CommentsPanelListing = (props) => {
  const { comments } = props;
  return (
    <div>
      {comments &&
        comments.length > 0 &&
        comments.map((data, index) => (
          <CommentsPanelListingItem key={index} data={data} />
        ))}
    </div>
  );
};

const mapStateToProps = (state) => {
  const { data } = state.commentsPanel;

  return {
    comments: data,
  };
};

CommentsPanelListing.propTypes = {
  comments: PropTypes.array,
};

export default connect(mapStateToProps)(CommentsPanelListing);
