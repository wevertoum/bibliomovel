package br.ufg.inf.level6.bibliomovel.util

import android.support.annotation.DrawableRes
import br.ufg.inf.level6.bibliomovel.R

enum class AlertType(@DrawableRes internal val path: Int) {

    // THAT IS ONLY AN EXAMPLE, YOU CAN REPLACE WITH YOUR ICONS FILE NAMES
    WARNING(R.drawable.ic_warning),
    SUCCESS(R.drawable.ic_success),
    INFO(R.drawable.ic_info),
    ERROR(R.drawable.ic_error),
    TIMEOUT(R.drawable.ic_timeout),
    SECURITY(R.drawable.ic_security);

}