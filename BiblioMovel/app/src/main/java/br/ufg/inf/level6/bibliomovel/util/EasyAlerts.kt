package br.ufg.inf.level6.bibliomovel.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import br.ufg.inf.level6.bibliomovel.R
import br.ufg.inf.level6.bibliomovel.util.AlertType.*

typealias Method = () -> Unit

class EasyAlerts {

    private var alert: AlertDialog? = null

    fun showGenericNeutralAlert(
            type: AlertType?,
            title: String,
            msg: String,
            context: Context,
            callback: Method?
    ) {
        if (alert != null) { alert!!.dismiss(); alert = null }

        val alertBuilder = android.support.v7.app.AlertDialog.Builder(context, android.app.AlertDialog.THEME_HOLO_DARK)

        alertBuilder.setIcon(type?.path ?: INFO.path)
        alertBuilder.setTitle(title.toUpperCase())
        alertBuilder.setMessage(msg)
        alertBuilder.setNeutralButton("Ok") {
            _,
            _ ->
            if (callback != null) {
                callback()
            } else alert!!.dismiss()
        }

        alert = alertBuilder.create()
        alert!!.setCancelable(false)
        alert!!.show()
    }

    fun showNeutralAlertToOpenActivity(
            type: AlertType?,
            title: String,
            msg: String,
            context: Context,
            targetActivity: Class<*>?
    ) {
        if (alert != null) { alert!!.dismiss(); alert = null }

        val alertBuilder = android.support.v7.app.AlertDialog.Builder(context)
        alertBuilder.setIcon(type?.path ?: INFO.path)
        alertBuilder.setTitle(title.toUpperCase())
        alertBuilder.setMessage(msg)
        alertBuilder.setNeutralButton("Ok") {
            _,
            _ ->
            if (targetActivity != null) {
                val intent = Intent(context, targetActivity)
                context.startActivity(intent)
            }
            alert!!.dismiss()
        }

        alert = alertBuilder.create()
        alert!!.setCancelable(false)
        alert!!.show()
    }

    fun showGenericDecisionAlert(
            type: AlertType?,
            title: String,
            msg: String,
            context: Context,
            positiveCallback: Pair<String, Method>?,
            negativeCallback: Pair<String, Method>?
    ) {
        if (alert != null) { alert!!.dismiss(); alert = null }

        val alertBuilder = android.support.v7.app.AlertDialog.Builder(context)
        alertBuilder.setIcon(type?.path ?: INFO.path)
        alertBuilder.setTitle(title.toUpperCase())
        alertBuilder.setMessage(msg)
        alertBuilder.setPositiveButton(positiveCallback?.first ?: "Yes") {
            _,
            _ -> if (positiveCallback != null) {
            positiveCallback.second()
        } else alert!!.dismiss()
        }
        alertBuilder.setNegativeButton(negativeCallback?.first ?: "No") {
            _,
            _ -> if (negativeCallback != null) {
            negativeCallback.second()
        } else alert!!.dismiss()
        }

        @Suppress("DEPRECATION")
        alert!!.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.colorPrimary))

        alert = alertBuilder.create()
        alert!!.setCancelable(false)
        alert!!.show()
    }

}