package com.victormartin.archmvvm.ui.view.listener

import com.victormartin.archmvvm.domain.model.DataDomainModel

interface ItemClickListener {

    fun onItemClick(playerPositionOnList: Int, data: DataDomainModel)

}