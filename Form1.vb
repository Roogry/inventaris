Imports System.Data.SqlClient

Public Class Form1
    Private Sub btnLogin_Click(sender As Object, e As EventArgs) Handles btnLogin.Click
        If validation(Me) Then
            Try
                openConn()
                query = "SELECT * FROM MsEmployee WHERE Email = '" & edtEmail.Text & "' AND Password = '" & SHA512(edtPassword.Text) & "'"
                command = New SqlCommand(query, conn)
                reader = command.ExecuteReader

                If reader.Read Then
                    If reader("Position") = "admin" Then
                        setSession()
                        Me.Hide()
                        NavAdmin.Show()
                    ElseIf reader("Position") = "chef" Then
                        setSession()
                        Me.Hide()
                        NavChef.Show()
                    ElseIf reader("Position") = "cashier" Then
                        setSession()
                        Me.Hide()
                        NavCashier.Show()
                    End If
                Else
                    MsgBox("Email atau Password salah")
                End If
            Catch ex As Exception
                MsgBox(ex.Message & ex.StackTrace)
            End Try
        End If
    End Sub

    Private Sub setSession()
        sessionId = reader("Id")
        sessionRole = reader("Position")
        sessionName = reader("Name")
        edtEmail.Text = ""
        edtPassword.Text = ""
    End Sub
End Class
