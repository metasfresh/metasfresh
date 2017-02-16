import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

class PaginationContextShortcuts extends Component {
    constructor(props){
        super(props);
    }

    handleShortcuts = (action, event) => {
        const {
            handleFirstPage, handleLastPage,
            handlePrevPage, handleNextPage
        } = this.props;

        switch (action) {
            case 'FIRST_PAGE':
                return handleFirstPage();
            case 'LAST_PAGE':
                return handleLastPage();
            case 'NEXT_PAGE':
                return handleNextPage();
            case 'PREV_PAGE':
                return handlePrevPage();
        }
    }

    render() {
        return (
            <Shortcuts
                name="PAGINATION_CONTEXT"
                handler = { this.handleShortcuts }
                targetNodeSelector = "body"
                isolate = { true }
                preventDefault = { true }
                stopPropagation = { true }
            />
        )
    }
}

export default PaginationContextShortcuts;
