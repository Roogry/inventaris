Imports System.Data.SqlClient
Imports System.Text.RegularExpressions

Public Class FormChangePass
    Private Sub btnSave_Click(sender As Object, e As EventArgs) Handles btnSave.Click
        If validation(Me) Then
            Try
                openConn()
                query = "SELECT Password FROM MsEmployee WHERE Id = '" & sessionId & "'"
                command = New SqlCommand(query, conn)
                reader = command.ExecuteReader

                If reader.Read Then
                    If reader(0) = SHA512(edtOld.Text) Then
                        If edtNew.Text = edtConfirm.Text Then
                            If Regex.IsMatch(edtNew.Text, "[A-Z]") And Regex.IsMatch(edtNew.Text, "[a-z]") And Regex.IsMatch(edtNew.Text, "\d") Then
                                openConn()
                                query = "UPDATE MsEmployee SET Password = '" & SHA512(edtNew.Text) & "' WHERE Id = '" & sessionId & "'"
                                command = New SqlCommand(query, conn)
                                command.ExecuteNonQuery()
                                MsgBox("Password berhasil di ganti")
                            Else
                                MsgBox("Password harus mengandung huruf besar, kecil, dan angka")
                            End If
                        Else
                            MsgBox("Password baru dan passowrd konfirmasi tidak sama")
                        End If
                    Else
                        MsgBox("Password lama salah")
                    End If
                Else
                    MsgBox("Gagal memuat password lama")
                End If

            Catch ex As Exception
                MsgBox(ex.Message & ex.StackTrace)
            End Try
        End If
    End Sub

    Private Sub FormChangePass_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        lastForm.Enabled = True
    End Sub
End Class