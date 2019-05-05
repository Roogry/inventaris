Public Class NavCashier

    Dim toLogin As Boolean = True

    Private Sub btnChangePass_Click(sender As Object, e As EventArgs) Handles btnChangePass.Click
        lastForm = Me
        Me.Enabled = False
        FormChangePass.Show()
    End Sub

    Private Sub NavCashier_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        txtNama.Text = sessionName
    End Sub

    Private Sub btnPayment_Click(sender As Object, e As EventArgs) Handles btnPayment.Click
        toLogin = False
        Me.Close()
        FomTest.Show()
    End Sub

    Private Sub btnLogout_Click(sender As Object, e As EventArgs) Handles btnLogout.Click
        Me.Close()
    End Sub

    Private Sub NavCashier_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        If toLogin Then
            Form1.Show()
        End If
    End Sub
End Class