import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


/**
 * Confirmation popup on deleting notes
 */
class ConfirmationDialogFragment : DialogFragment() {

    interface ConfirmationDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    private var listener: ConfirmationDialogListener? = null


    /**
     * When dialog is created, have two options 'yes' and 'no'
     * will delete stuff on 'yes'
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Deleting. Are you sure?")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener?.onDialogPositiveClick(this)
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener?.onDialogNegativeClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setConfirmationDialogListener(listener: ConfirmationDialogListener) {
        this.listener = listener
    }
}
