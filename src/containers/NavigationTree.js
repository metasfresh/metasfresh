import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import DebounceInput from 'react-debounce-input';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';

import {
  nodePathsRequest,
  queryPathsRequest,
  rootRequest,
} from '../actions/MenuActions';
import { openModal } from '../actions/WindowActions';
import Container from '../components/Container';
import MenuOverlayContainer from '../components/header/MenuOverlayContainer';

class NavigationTree extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rootResults: {
        caption: '',
        children: [],
      },
      query: '',
      queriedResults: [],
      deepNode: null,
    };
  }

  componentDidMount = () => {
    this.getData();

    document.getElementById('search-input').focus();
  };

  getData = async doNotResetState => {
    const { query } = this.state;

    if (doNotResetState && query) {
      await this.queryRequest(query);
    } else {
      try {
        const response = await rootRequest();

        await new Promise(resolve =>
          this.setState(
            {
              ...this.state,
              rootResults: response.data,
              queriedResults: response.data.children,
              query: '',
            },
            resolve
          )
        );
      } catch (error) {
        if (error.response && error.response.status === 404) {
          await new Promise(resolve =>
            this.setState(
              {
                ...this.state,
                queriedResults: [],
                rootResults: {},
                query: '',
              },
              resolve
            )
          );
        }
      }
    }
  };

  updateData = async () => {
    await this.getData(true);
  };

  openModal = (windowType, type, caption, isAdvanced) => {
    const { dispatch } = this.props;

    dispatch(openModal(caption, windowType, type, null, null, isAdvanced));
  };

  handleQuery = async event => {
    event.preventDefault();

    if (event.target.value) {
      await new Promise(resolve =>
        this.setState(
          {
            query: event.target.value,
          },
          resolve
        )
      );

      await this.queryRequest(event.target.value);
    } else {
      await this.getData();

      this.clearValue();
    }
  };

  queryRequest = async value => {
    try {
      const response = await queryPathsRequest(value, '', true);

      await new Promise(resolve =>
        this.setState(
          {
            queriedResults: response.data.children,
          },
          resolve
        )
      );
    } catch (error) {
      if (error.response && error.response.status === 404) {
        await new Promise(resolve =>
          this.setState(
            {
              queriedResults: [],
              rootResults: {},
            },
            resolve
          )
        );
      }
    }
  };

  clearValue = () => {
    document.getElementById('search-input').value = '';
  };

  handleClear = async event => {
    event.preventDefault();

    await this.getData();

    this.clearValue();
  };

  handleKeyDown = e => {
    const input = document.getElementById('search-input');
    const firstMenuItem = document.getElementsByClassName('js-menu-item')[0];
    let prevParentSibling = document.activeElement.previousSibling;

    switch (e.key) {
      case 'ArrowDown':
        if (document.activeElement === input) {
          firstMenuItem.focus();
        }
        break;
      case 'ArrowUp':
        if (document.activeElement.classList.contains('js-menu-header')) {
          prevParentSibling.children[0] &&
            prevParentSibling.children[0].classList.contains(
              'js-menu-header'
            ) &&
            prevParentSibling.children[0].focus();
        }

        if (document.activeElement.classList.contains('js-menu-item')) {
          this.handleArrowUp();
        }
        break;
      case 'Tab':
        e.preventDefault();
        if (document.activeElement === input) {
          firstMenuItem.focus();
        } else {
          input.focus();
        }
        break;
      case 'Enter':
        e.preventDefault();
        document.activeElement.childNodes[0].childNodes[0].click();
        break;
    }
  };

  handleArrowUp() {
    let prevSiblings = document.activeElement.previousSibling;
    if (prevSiblings && prevSiblings.classList.contains('input-primary')) {
      document.getElementById('search-input-query').focus();
    } else if (
      prevSiblings &&
      prevSiblings.classList.contains('js-menu-item')
    ) {
      document.activeElement.previousSibling.focus();
    } else {
      this.handleGroupUp();
    }
  }

  findPreviousGroup() {
    let elem = document.activeElement.parentElement;
    let i = 0;
    while (
      !(
        (elem &&
          elem.classList.contains('js-menu-container') &&
          elem.previousSibling &&
          elem.previousSibling.children.length !== 0) ||
        (elem && elem.classList.contains('js-menu-main-container') && i < 100)
      )
    ) {
      elem = elem && elem.parentElement;
      i++;
    }

    return elem.previousSibling;
  }

  handleGroupUp() {
    const previousMainGroup = this.findPreviousGroup();
    const previousGroup = document.activeElement.parentElement.previousSibling;

    if (previousGroup && previousGroup.classList.contains('js-menu-item')) {
      previousGroup.focus();
    } else {
      if (previousGroup && previousGroup.children.length > 0) {
        this.selectLastItem(previousGroup);
      } else if (previousMainGroup) {
        this.selectLastItem(previousMainGroup);
      } else {
        document.activeElement.previousSibling.focus();
      }
    }
  }

  selectLastItem(previousGroup) {
    const listChildren = previousGroup.childNodes;
    const lastChildren = listChildren[listChildren.length - 1];
    if (listChildren.length == 1) {
      listChildren[0].focus && listChildren[0].focus();
    } else {
      if (lastChildren.classList.contains('js-menu-item')) {
        lastChildren.focus();
      } else {
        if (
          lastChildren.children[
            lastChildren.children.length - 1
          ].classList.contains('js-menu-item')
        ) {
          lastChildren.children[lastChildren.children.length - 1].focus();
        } else {
          lastChildren.children[lastChildren.children.length - 1]
            .getElementsByClassName('js-menu-item')
            [
              lastChildren.children[
                lastChildren.children.length - 1
              ].getElementsByClassName('js-menu-item').length - 1
            ].focus();
        }
      }
    }
  }

  onRedirect = (elementId, isNew, type) => {
    const { dispatch } = this.props;
    dispatch(push('/' + (type ? type : 'window') + '/' + elementId));
  };

  handleNewRedirect = elementId => {
    const { dispatch } = this.props;
    dispatch(push('/window/' + elementId + '/new'));
  };

  handleDeeper = (e, nodeId) => {
    e.preventDefault();

    nodePathsRequest(nodeId, 4).then(response => {
      this.setState(
        Object.assign({}, this.state, {
          deepNode: response.data,
        })
      );
    });
  };
  handleClickBack = e => {
    e.preventDefault();

    this.setState(
      Object.assign({}, this.state, {
        deepNode: null,
      })
    );
  };

  renderTree = () => {
    const { rootResults, queriedResults, query } = this.state;

    return (
      <div className="sitemap">
        <div className="search-wrapper">
          <div className="input-flex input-primary">
            <i className="input-icon meta-icon-preview" />

            <DebounceInput
              debounceTimeout={250}
              type="text"
              id="search-input"
              className="input-field"
              placeholder={counterpart.translate('window.type.placeholder')}
              onChange={this.handleQuery}
              onKeyDown={this.handleKeyDown}
            />

            {query && (
              <i
                className="input-icon meta-icon-close-alt pointer"
                onClick={this.handleClear}
              />
            )}
          </div>
        </div>

        <p className="menu-overlay-header menu-overlay-header-main menu-overlay-header-spaced">
          {rootResults.caption}
        </p>

        <div className="column-wrapper">
          {queriedResults &&
            queriedResults.map((subitem, subindex) => (
              <MenuOverlayContainer
                key={subindex}
                printChildren={true}
                showBookmarks={true}
                openModal={this.openModal}
                updateData={this.updateData}
                onKeyDown={this.handleKeyDown}
                onClickOnFolder={this.handleDeeper}
                onRedirect={this.handleRedirect}
                onNewRedirect={this.handleNewRedirect}
                {...subitem}
              />
            ))}

          {queriedResults.length === 0 &&
            query !== '' && <span>There are no results</span>}
        </div>
      </div>
    );
  };

  render() {
    const { rawModal, modal } = this.props;
    const { rootResults } = this.state;

    return (
      <Container siteName="Sitemap" {...{ modal, rawModal }}>
        {this.renderTree(rootResults)}
      </Container>
    );
  }
}

function mapStateToProps(state) {
  const { windowHandler } = state;

  const { modal, rawModal } = windowHandler || {
    modal: {},
    rawModal: {},
  };

  return {
    modal,
    rawModal,
  };
}

NavigationTree.propTypes = {
  dispatch: PropTypes.func.isRequired,
  modal: PropTypes.object.isRequired,
  rawModal: PropTypes.object.isRequired,
};

export default connect(mapStateToProps)(NavigationTree);
