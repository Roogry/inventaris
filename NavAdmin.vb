Public Class NavAdmin

    Dim toLogin As Boolean = True

    Private Sub btnLogout_Click(sender As Object, e As EventArgs) Handles btnLogout.Click
        Me.Close()
    End Sub

    Private Sub btnChangePass_Click(sender As Object, e As EventArgs) Handles btnChangePass.Click
        lastForm = Me
        Me.Enabled = False
        FormChangePass.Show()
    End Sub

    Private Sub NavAdmin_Load(sender As Object, e As EventArgs) Handles Me.Load
        txtNama.Text = sessionName
    End Sub

    Private Sub btnEmployee_Click(sender As Object, e As EventArgs) Handles btnEmployee.Click
        toLogin = False
        Me.Close()
        FormEmployee.Show()
    End Sub

    Private Sub btnMember_Click(sender As Object, e As EventArgs) Handles btnMember.Click
        toLogin = False
        Me.Close()
        FormMember.Show()
    End Sub

    Private Sub btnMenu_Click(sender As Object, e As EventArgs) Handles btnMenu.Click
        toLogin = False
        Me.Close()
        FormMenu.Show()
    End Sub

    Private Sub btnOrder_Click(sender As Object, e As EventArgs) Handles btnOrder.Click
        toLogin = False
        Me.Close()
        FormOrder.Show()
    End Sub

    Private Sub btnReport_Click(sender As Object, e As EventArgs) Handles btnReport.Click
        toLogin = False
        Me.Close()
        FormReport.Show()
    End Sub

    Private Sub NavAdmin_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        If toLogin Then
            Form1.Show()
        End If
    End Sub
End Class