import {
  UPDATE_COMMENTS_PANEL,
  UPDATE_COMMENTS_PANEL_TEXT_INPUT,
  UPDATE_COMMENTS_PANEL_OPEN_FLAG,
} from '../constants/CommentTypes';

export function updateCommentsPanel(data) {
  return {
    type: UPDATE_COMMENTS_PANEL,
    payload: data,
  };
}

export function updateCommentsPanelTextInput(data) {
  return {
    type: UPDATE_COMMENTS_PANEL_TEXT_INPUT,
    payload: data,
  };
}

export function updateCommentsPanelOpenFlag(data) {
  return {
    type: UPDATE_COMMENTS_PANEL_OPEN_FLAG,
    payload: data,
  };
}
