Imports System.Data.SqlClient

Public Class FormViewOrder
    Private Sub FormViewOrder_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        cbSort.SelectedItem = "Menu"
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
    End Sub

    Private Sub FormViewOrder_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        NavChef.Show()
    End Sub

    Private Sub cbOrder_SelectedIndexChanged(sender As Object, e As EventArgs) Handles cbOrder.SelectedIndexChanged
        getOrder()
    End Sub

    Private Sub getOrder(Optional search As String = "")
        dg.Rows.Clear()
        Try
            Dim status As New ArrayList
            Dim row As String()

            openConn()
            query = "SELECT a.Id, c.Name 'Menu', a.Qty, a.status FROM OrderDetail a INNER JOIN MsMenu c On a.Menuid = c.Id Where a.OrderId = '" & cbOrder.Text & "' "
            If search <> "" Then
                query &= "AND (c.Name LIKE '%" & search & "%' OR a.Qty LIKE '%" & search & "%' OR a.status LIKE '%" & search & "%') "
            End If
            query &= "ORDER BY '" & cbSort.SelectedItem & "' ASC"

            command = New SqlCommand(query, conn)
            reader = command.ExecuteReader
            While reader.Read
                row = {reader(0), reader(1), reader(2)}
                dg.Rows.Add(row)
                status.Add(reader(3))
            End While

            For i As Integer = 0 To status.Count - 1
                dg.Item(3, i).Value = status(i)
            Next
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try
    End Sub

    Private Sub btnSearch_Click(sender As Object, e As EventArgs) Handles btnSearch.Click
        getOrder(tbSearch.Text)
    End Sub

    Private Sub cbSort_SelectedIndexChanged(sender As Object, e As EventArgs) Handles cbSort.SelectedIndexChanged
        getOrder(tbSearch.Text)
    End Sub

    Private Sub dg_EditingControlShowing(sender As Object, e As DataGridViewEditingControlShowingEventArgs) Handles dg.EditingControlShowing
        Dim combo As ComboBox = TryCast(e.Control, ComboBox)
        AddHandler combo.SelectedIndexChanged, AddressOf combo_selectedIndexChange
    End Sub

    Private Sub combo_selectedIndexChange(sender As Object, e As EventArgs)
        Dim combo As ComboBox = TryCast(sender, ComboBox)

        putStatus(combo.Text)

        RemoveHandler combo.SelectedIndexChanged, AddressOf combo_selectedIndexChange
    End Sub

    Private Sub putStatus(status As String)
        Dim i As Integer = dg.CurrentRow.Index
        Try
            openConn()
            query = "UPDATE OrderDetail SET Status = '" & status & "' WHERE Id = '" & dg.Item(0, i).Value & "'"
            command = New SqlCommand(query, conn)
            command.ExecuteNonQuery()

        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try
    End Sub
End Class