import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import history from '../../services/History';
import { setFilter } from '../../actions/ListActions';
import { referencesEventSource } from '../../api/documentReferences';
import {
  buildRelatedDocumentsViewUrl,
  mergePartialGroupToGroupsArray,
} from '../../utils/documentReferencesHelper';
import SpinnerOverlay from '../app/SpinnerOverlay';
import DocumentReferenceGroup from './DocumentReferenceGroup';

/**
 * Document related documents (references) component
 */
class DocumentReferences extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      data: null,
    };
  }

  componentDidMount = () => {
    const { windowId, documentId } = this.props;

    this.closeEventSource();
    this.eventSource = referencesEventSource({
      windowId: windowId,
      documentId: documentId,

      onPartialResult: (partialGroup) => {
        const data = this.state.data || [];
        this.setState(
          {
            ...this.state,
            loading: true,
            data: mergePartialGroupToGroupsArray(data, partialGroup),
          },
          () => {
            this.referenced && this.referenced.focus();
          }
        );
      },

      onComplete: () => {
        this.setState({
          ...this.state,
          loading: false,
        });

        this.closeEventSource();
      },
    });
  };

  componentWillUnmount = () => {
    this.closeEventSource();
  };

  closeEventSource = () => {
    if (this.eventSource) {
      this.eventSource.close();
      delete this.eventSource;
    }
  };

  handleReferenceClick = ({
    targetWindowId,
    referenceId,
    filter,
    ctrlKeyPressed,
  }) => {
    const { dispatch, windowId, documentId } = this.props;

    const url = buildRelatedDocumentsViewUrl({
      targetWindowId,
      windowId,
      documentId,
      referenceId,
    });

    dispatch(setFilter(filter, targetWindowId));

    if (ctrlKeyPressed) {
      window.open(url, '_blank');
    } else {
      history.push(url);
    }
  };

  handleKeyDown = (e) => {
    const active = document.activeElement;

    const keyHandler = (e, dir) => {
      e.preventDefault();

      const sib = dir ? 'nextSibling' : 'previousSibling';

      if (active.classList.contains('js-subheader-item')) {
        if (!active[sib]) {
          return;
        }
        if (active[sib].classList.contains('js-subheader-item')) {
          active[sib].focus();
        } else {
          active[sib][sib] && active[sib][sib].focus();
        }
      } else {
        active.getElementsByClassName('js-subheader-item')[0].focus();
      }
    };

    switch (e.key) {
      case 'ArrowDown':
        keyHandler(e, true);
        break;
      case 'ArrowUp':
        keyHandler(e, false);
        break;
      case 'Enter':
        e.preventDefault();
        document.activeElement.dispatchEvent(
          new MouseEvent('click', {
            bubbles: true,
            cancelable: true,
            view: window,
            ctrlKey: e.ctrlKey,
          })
        );
        break;
    }
  };

  render() {
    const { loading } = this.state;
    return (
      <div
        onKeyDown={this.handleKeyDown}
        ref={(c) => (this.referenced = c)}
        tabIndex={0}
      >
        {this.renderData()}
        {loading ? (
          <div className="docref-spinner-wrapper">
            <SpinnerOverlay iconSize={50} />
          </div>
        ) : null}
      </div>
    );
  }

  renderData = () => {
    const { loading, data } = this.state;

    if (data && data.length) {
      return data.map((group) => (
        <DocumentReferenceGroup
          key={group.caption}
          caption={group.caption}
          references={group.references}
          onReferenceItemClick={this.handleReferenceClick}
        />
      ));
    } else if (!loading) {
      return (
        <div className="subheader-item subheader-item-disabled">
          {counterpart.translate('window.sideList.referenced.empty')}
        </div>
      );
    }
  };
}

DocumentReferences.propTypes = {
  windowId: PropTypes.string.isRequired,
  documentId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default connect()(DocumentReferences);
