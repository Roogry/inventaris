Imports System.Data.SqlClient

Public Class FormPayment
    Private Sub FormPayment_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Try
            openConn()
            query = "SELECT Id FROM OrderHeader WHERE PaymentType IS NULL OR PaymentType = ''"
            command = New SqlCommand(query, conn)
            reader = command.ExecuteReader
            While reader.Read
                cbOrder.Items.Add(reader(0))
            End While
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try

        disPay()
    End Sub

    Private Sub disPay()
        txtCC.Visible = False
        txtBank.Visible = False
        tbNumber.Visible = False
        cbBank.Visible = False
    End Sub

    Private Sub FormPayment_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        NavCashier.Show()
    End Sub

    Private Sub cbPayment_SelectedIndexChanged(sender As Object, e As EventArgs) Handles cbPayment.SelectedIndexChanged
        If cbPayment.SelectedIndex = 0 Then
            cbBank.Visible = True
            cbBank.AccessibleName = "bank"
            txtBank.Visible = True
            txtCC.Text = "Card Number"
        Else
            cbBank.Visible = False
            cbBank.AccessibleName = "noValid"
            txtBank.Visible = False
            txtCC.Text = "Cash"
        End If
    End Sub

    Private Sub cbOrder_SelectedIndexChanged(sender As Object, e As EventArgs) Handles cbOrder.SelectedIndexChanged
        Try
            dgOrder.Rows.Clear()
            openConn()
            query = "SELECT a.Name, b.Qty, a.Price FROM OrderDetail b INNER JOIN MsMenu a ON b.MenuId = a.Id WHERE b.OrderId = '" & cbOrder.SelectedItem.ToString & "'"
            command = New SqlCommand(query, conn)
            reader = command.ExecuteReader

            Dim hrgttl As Integer = 0
            Dim total As Integer = 0

            While reader.Read
                total = reader(1) * reader(2)
                hrgttl += total

                Dim row As String()
                row = {reader(0), reader(1), reader(2), total}
                dgOrder.Rows.Add(row)
            End While

            txtTotal.Text = hrgttl.ToString
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try
    End Sub

    Private Sub btnSave_Click(sender As Object, e As EventArgs) Handles btnSave.Click
        If validation(Me) Then
            If cbPayment.SelectedIndex = 0 Then
                If IsNumeric(tbNumber.Text) Then
                    If tbNumber.Text.Length <= 16 Then
                        Try
                            openConn()
                            query = "UPDATE OrderHeader SET PaymentType = 'credit', CardNumber = '" & tbNumber.Text & "', Bank = '" & cbBank.Text & "' WHERE Id = '" & cbOrder.Text & "'"
                            command = New SqlCommand(query, conn)
                            command.ExecuteNonQuery()
                            MsgBox("Transaksi Sukses")
                            Me.Close()
                        Catch ex As Exception
                            MsgBox(ex.Message & ex.StackTrace)
                        End Try
                    Else
                        MsgBox("Card Number tidak boleh lebih dari 16 angka")
                    End If
                Else
                    MsgBox("Card Number Hanya boleh angka")
                End If
            Else
                If IsNumeric(tbNumber.Text) Then
                    If Convert.ToInt32(tbNumber.Text) >= Convert.ToInt32(txtTotal.Text) Then
                        Try
                            openConn()
                            query = "UPDATE OrderHeader SET PaymentType = 'cash' WHERE Id = '" & cbOrder.SelectedItem.ToString & "'"
                            command = New SqlCommand(query, conn)
                            command.ExecuteNonQuery()
                            MsgBox("Pembayaran sukse, uang Kembaliannya Rp." & Convert.ToInt32(tbNumber.Text) - Convert.ToInt32(txtTotal.Text))
                            Me.Close()
                        Catch ex As Exception
                            MsgBox(ex.Message & ex.StackTrace)
                        End Try
                    Else
                        MsgBox("Uang cash tidak cukup")
                    End If
                Else
                    MsgBox("Cash hanya boleh angka")
                End If
            End If
        End If
    End Sub
End Class