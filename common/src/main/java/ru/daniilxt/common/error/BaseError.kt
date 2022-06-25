package ru.daniilxt.common.error

import androidx.annotation.StringRes
import ru.daniilxt.common.R

sealed class BaseError(@StringRes val errResId: Int) : ErrorEntity {
    object Error : BaseError(R.string.error_unknown)
    object Unknown : BaseError(R.string.error_unknown)
}
