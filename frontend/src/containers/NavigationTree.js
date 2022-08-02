import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import DebounceInput from 'react-debounce-input';
import { connect } from 'react-redux';
import SpinnerOverlay from '../components/app/SpinnerOverlay';
import history from '../services/History';
import { nodePathsRequest, queryPathsRequest, rootRequest } from '../api';
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

  getData = async (doNotResetState) => {
    const { query } = this.state;

    if (doNotResetState && query) {
      await this.queryRequest(query);
    } else {
      try {
        const response = await rootRequest();

        await new Promise((resolve) =>
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
          await new Promise((resolve) =>
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

    dispatch(
      openModal({
        title: caption,
        windowId: windowType,
        modalType: type,
        isAdvanced,
      })
    );
  };

  handleQuery = async (event) => {
    event.preventDefault();

    if (event.target.value) {
      await new Promise((resolve) =>
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

  queryRequest = async (value) => {
    this.setState({ pendingQuery: true, query: value });
    const { query } = this.state;
    await queryPathsRequest(value, '', true)
      .then((res) => {
        this.setState({
          queriedResults: query === value ? res.data.children : [],
          pendingQuery: false,
          query,
        });
      })
      .catch((error) => {
        if (error.response && error.response.status === 404) {
          this.setState({
            queriedResults: [],
            rootResults: {},
            pendingQuery: false,
            query,
          });
        }
      });
  };

  clearValue = () => {
    document.getElementById('search-input').value = '';
  };

  handleClear = async (event) => {
    event.preventDefault();

    await this.getData();

    this.clearValue();
  };

  handleKeyDown = (e) => {
    const input = document.getElementById('search-input');
    const firstMenuItem = document.getElementsByClassName('js-menu-item')[0];
    let prevParentSibling = document.activeElement.previousSibling;
    const elementToEnter = document.activeElement.childNodes[0];
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
        elementToEnter.childNodes[0] && elementToEnter.childNodes[0].click();
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

  handleRedirect = (elementId, isNew, type) => {
    history.push('/' + (type ? type : 'window') + '/' + elementId);
  };

  handleNewRedirect = (elementId) => {
    history.push('/window/' + elementId + '/new');
  };

  handleDeeper = (e, nodeId) => {
    e.preventDefault();

    nodePathsRequest(nodeId, 4).then((response) => {
      this.setState(
        Object.assign({}, this.state, {
          deepNode: response.data,
        })
      );
    });
  };
  handleClickBack = (e) => {
    e.preventDefault();

    this.setState(
      Object.assign({}, this.state, {
        deepNode: null,
      })
    );
  };

  /**
   * @method renderMenuOverlayContainer
   * @summary - renders the menu items for the right and left columns within the sitemap
   * @param {object} subitem
   * @param {number} subindex
   * @returns MenuOverlayContainer
   */
  renderMenuOverlayContainer = (subitem, subindex) => {
    return (
      <MenuOverlayContainer
        key={subindex}
        printChildren={true}
        showBookmarks={true}
        openModal={this.openModal}
        onUpdateData={this.updateData}
        onKeyDown={this.handleKeyDown}
        handleClickOnFolder={this.handleDeeper}
        handleRedirect={this.handleRedirect}
        handleNewRedirect={this.handleNewRedirect}
        menuType="sitemap"
        levelType="navigationTree"
        {...subitem}
      />
    );
  };

  renderTree = () => {
    const { queriedResults, query, pendingQuery } = this.state;

    let sitemapLeftColItems = queriedResults.filter(
      (colItem, i) => i % 2 === 0
    );
    let sitemapRightColItems = queriedResults.filter(
      (colItem, i) => i % 2 === 1
    );

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

        {/* sitemap items are listed using this */}
        {!pendingQuery && (
          <div className="column-wrapper">
            <div className="sitemap-column-left">
              {sitemapLeftColItems &&
                sitemapLeftColItems.map((subitem, subindex) =>
                  this.renderMenuOverlayContainer(subitem, subindex)
                )}
            </div>
            <div className="sitemap-column-right">
              {sitemapRightColItems &&
                sitemapRightColItems.map((subitem, subindex) =>
                  this.renderMenuOverlayContainer(subitem, subindex)
                )}
            </div>

            {queriedResults.length === 0 && query !== '' && (
              <span>{counterpart.translate('window.noResults.caption')}</span>
            )}
          </div>
        )}

        {/* display the spinner while loading */}
        {pendingQuery && (
          <div className="sitemap-spinner">
            <SpinnerOverlay iconSize={50} />
          </div>
        )}
      </div>
    );
  };

  render() {
    const { rawModal, modal, pluginModal } = this.props;
    const { rootResults } = this.state;

    return (
      <Container siteName="Sitemap" {...{ modal, rawModal, pluginModal }}>
        {this.renderTree(rootResults)}
      </Container>
    );
  }
}

function mapStateToProps(state) {
  const { windowHandler } = state;

  const { modal, rawModal, pluginModal } = windowHandler || {
    modal: {},
    rawModal: {},
    pluginModal: {},
  };

  return {
    modal,
    rawModal,
    pluginModal,
  };
}

NavigationTree.propTypes = {
  dispatch: PropTypes.func.isRequired,
  modal: PropTypes.object.isRequired,
  rawModal: PropTypes.object.isRequired,
  pluginModal: PropTypes.object.isRequired,
};

export default connect(mapStateToProps)(NavigationTree);
