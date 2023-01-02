import React, { useEffect, useRef, Fragment } from 'react';
import { callAPI } from '../../actions/WindowActions';
import { updateCommentsPanelTextInput } from '../../actions/CommentsPanelActions';
import { connect } from 'react-redux';
import { PropTypes } from 'prop-types';
import { translateCaption } from '../../utils/index';

export const CommentsPanelForm = (props) => {
  const { textInput, postComment, updateText, docId, windowId } = props;
  const textRef = useRef(null);
  const sendButton = translateCaption('window.comments.add');

  /**
   * Focus by default on the text input
   */
  useEffect(() => {
    textRef.current.focus();
  }, []);

  return (
    <Fragment>
      <form className="col-12">
        <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
          <div className="row">
            <div className="col-lg-10 col-md-10 col-sm-10 float-left">
              <div className="input-body-container">
                <span />
                <div>
                  <div className="input-block input-primary pulse-off">
                    <textarea
                      ref={textRef}
                      autoComplete="off"
                      className="input-field js-input-field"
                      onKeyPress={(e) => updateText(e.target.value)}
                      onChange={(e) => updateText(e.target.value)}
                      tabIndex="0"
                      value={textInput}
                    />
                  </div>
                </div>
              </div>
            </div>
            <div className="col-lg-2 col-md-2 col-sm-2 float-right">
              <button
                className="btn btn-sm btn-block btn-meta-success"
                onClick={(e) => {
                  e.preventDefault();
                  !textInput && textRef.current.focus();
                  textInput &&
                    postComment({
                      windowId,
                      docId,
                      tabId: null,
                      rowId: null,
                      target: 'comments',
                      verb: 'POST',
                      data: textInput,
                    });
                }}
                tabIndex={0}
              >
                {sendButton}
              </button>
            </div>
            <div className="clearfix" />
          </div>
        </div>
      </form>
    </Fragment>
  );
};

CommentsPanelForm.propTypes = {
  textInput: PropTypes.string,
  updateText: PropTypes.func,
  postComment: PropTypes.func,
  dispatch: PropTypes.func,
  docId: PropTypes.string,
  windowId: PropTypes.string,
};

const mapStateToProps = ({ commentsPanel }) => ({
  textInput: commentsPanel.textInput,
});

export default connect(mapStateToProps, {
  updateText: updateCommentsPanelTextInput,
  postComment: callAPI,
})(CommentsPanelForm);
