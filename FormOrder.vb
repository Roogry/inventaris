Imports System.Data.SqlClient

Public Class FormOrder

    Dim memberId As New ArrayList

    Private Sub FormOrder_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Try
            openConn()
            query = "SELECT * FROM MsMenu"
            adapter = New SqlDataAdapter(query, conn)
            dataset.Clear()
            adapter.Fill(dataset, "MenuOrder")
            With dg
                .DataSource = dataset
                .DataMember = "MenuOrder"
                .Columns(3).Visible = False
                .Columns(0).Visible = False
            End With
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try

        Try
            openConn()
            query = "SELECT Id, Name FROM MsMember"
            command = New SqlCommand(query, conn)
            reader = command.ExecuteReader
            While reader.Read
                memberId.Add(reader(0))
                Dim member As String = reader(0) & " - " & reader(1)
                cbMember.Items.Add(member)
            End While
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try

        pbMenu.ImageLocation = "MenuPic/nopict.png"
    End Sub

    Private Sub FormOrder_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        NavAdmin.Show()
    End Sub

    Private Sub setText(Optional name As String = "", Optional qty As String = "", Optional photo As String = "")
        edtName.Text = name
        edtQty.Text = qty
        pbMenu.ImageLocation = "MenuPic/" & photo
    End Sub

    Private Sub dg_CellClick(sender As Object, e As DataGridViewCellEventArgs) Handles dg.CellClick
        Dim i As Integer = dg.CurrentRow.Index
        With dg
            setText(.Item(1, i).Value, "1", .Item(3, i).Value)
        End With
    End Sub

    Private Sub btnAdd_Click(sender As Object, e As EventArgs) Handles btnAdd.Click
        If edtName.Text <> "" Then

            If IsNumeric(edtQty.Text) Then
                btnDelete.Enabled = True

                Dim i As Integer = dg.CurrentRow.Index
                Dim index As Integer = 0
                Dim operation As String = "add"
                Dim row As String()
                Dim menu, menuId As String
                Dim qty, carbo, protein, price, total, hargaTtl, carboTtl, proteinTtl As Integer

                With dg
                    menuId = .Item(0, i).Value
                    menu = .Item(1, i).Value
                    qty = edtQty.Text
                    carbo = .Item(4, i).Value * qty
                    protein = .Item(5, i).Value * qty
                    price = .Item(2, i).Value
                    total = price * qty
                End With

                hargaTtl = txtTotal.Text
                carboTtl = txtCarbo.Text
                proteinTtl = txtProtein.Text

                For x As Integer = 0 To dg2.RowCount - 1
                    If dg2.Item(1, x).Value = menu Then
                        operation = "update"
                        index = x
                    End If
                Next

                If operation = "add" Then
                    row = {menuId, menu, qty, carbo / qty, protein / qty, price, total}
                    dg2.Rows.Add(row)

                    txtTotal.Text = hargaTtl + total
                    txtCarbo.Text = carboTtl + carbo
                    txtProtein.Text = proteinTtl + protein
                Else
                    With dg2
                        Dim qtyLama As Integer = .Item(2, index).Value
                        Dim totalLama As Integer = .Item(6, index).Value

                        .Item(6, index).Value = totalLama + total
                        .Item(2, index).Value = qtyLama + qty

                        txtTotal.Text = hargaTtl + total
                        txtCarbo.Text = carboTtl + carbo
                        txtProtein.Text = proteinTtl + protein
                    End With
                End If
            End If

        Else
            MsgBox("Pilih Menu terlebih dulu")
        End If
    End Sub

    Private Sub btnDelete_Click(sender As Object, e As EventArgs) Handles btnDelete.Click
        Dim i As Integer = dg2.CurrentRow.Index

        With dg2
            txtTotal.Text -= .Item(6, i).Value
            txtCarbo.Text -= .Item(3, i).Value * .Item(2, i).Value
            txtProtein.Text -= .Item(4, i).Value * .Item(2, i).Value
        End With

        dg2.Rows.RemoveAt(i)

        If dg2.RowCount = 0 Then
            btnDelete.Enabled = False
            MsgBox("Orderan Anda Kosong")
        End If
    End Sub

    Private Sub btnOrder_Click(sender As Object, e As EventArgs) Handles btnOrder.Click
        If dg2.RowCount <> 0 Then
            If cbMember.Text <> "" Then
                Try
                    Dim id As String = orderId()
                    openConn()
                    query = "INSERT INTO OrderHeader(Id, EmployeeId, MemberId, Date) VALUES('" & id & "', 'EM0001', '" & memberId.Item(cbMember.SelectedIndex) & "', '" & DateTime.Now.ToString("yyyyMMdd") & "')"
                    command = New SqlCommand(query, conn)
                    command.ExecuteNonQuery()

                    For i As Integer = 0 To dg2.RowCount - 1
                        Try
                            openConn()
                            query = "INSERT INTO OrderDetail(OrderId, MenuId, Qty, Status) VALUES ('" & id & "', '" & dg2.Item(0, i).Value & "', '" & dg2.Item(2, i).Value & "', 'Preparing')"
                            command = New SqlCommand(query, conn)
                            command.ExecuteNonQuery()

                        Catch ex As Exception
                            MsgBox(ex.Message & ex.StackTrace)
                        End Try
                    Next
                    MsgBox("Orderan sudah masuk")
                    Me.Close()
                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try
            Else
                MsgBox("Pilih member yang mengorder")
            End If
        Else
            MsgBox("Orderan anda masih kosong")
        End If
    End Sub
End Class